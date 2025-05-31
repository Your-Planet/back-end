package kr.co.yourplanet.core.alimtalk.support;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class AlimTalkVariableResolver {

    private AlimTalkVariableResolver() {}

    private static final String ETC_VARIABLE_MAP_KEY = "etcMap";
    private static final Pattern ENTITY_PATTERN = Pattern.compile("#\\{(\\w+)\\.(\\w+)}"); // ex #{member.name}
    private static final Pattern ETC_PATTERN = Pattern.compile("#\\{(\\w+)}"); // ex #{authCode}

    /**
     * 알림톡 템플릿 문구 내에 포함된 #{객체.필드} 형태의 변수를 실제 객체의 필드 값으로 치환합니다.
     * Entity 의존성이 없는 변수는 {기타변수} 형태로 처리됩니다.
     *
     * 예시:
     *   templateText = "안녕하세요 #{member.name}님, 프로젝트 번호는 #{project.id}입니다. #{authCode}"
     *   contextObjectMap = Map.of("member", MemberEntity, "project", ProjectEntity,
     *                              "etcMap", Map.of("authCode", "123456")
     *                            )
     *
     * 위와 같은 경우 MemberEntity.getName(), ProjectEntity.getId() 를 리플렉션으로 호출해 템플릿 문자열 내 변수에 실제 값을 대입합니다.
     * "etcMap"에 등록된 변수는 전달받은 문자열로 치환됩니다.
     *
     * @param templateText #{도메인.필드} 형식의 변수가 포함된 템플릿 문구
     * @param contextObjectMap 템플릿에서 참조할 도메인 객체를 담은 맵 (예: "member" → member 객체)
     * @return 실제 값이 치환된 문자열
     */
    public static String resolve(String templateText, Map<String, Object> contextObjectMap) {
        String resolvedTemplateText = "";

        /* entityPattern 기준 1차 치환 */
        Matcher entityMatcher = ENTITY_PATTERN.matcher(templateText);

        StringBuilder entityResolveSb = new StringBuilder();
        while (entityMatcher.find()) {
            String objectKey = entityMatcher.group(1);  // member
            String fieldKey = entityMatcher.group(2);   // name

            Object targetObject = contextObjectMap.get(objectKey);
            String replacement = "";

            if (targetObject != null) {
                try {
                    Method getter = targetObject.getClass().getMethod("get" + StringUtils.capitalize(fieldKey));
                    Object value = getter.invoke(targetObject);
                    replacement = value != null ? value.toString() : "";
                } catch (Exception e) {
                    replacement = "";
                }
            }
            entityMatcher.appendReplacement(entityResolveSb, Matcher.quoteReplacement(replacement));
        }
        entityMatcher.appendTail(entityResolveSb);

        // 1차 치환 문자열 저장
        resolvedTemplateText = entityResolveSb.toString();

        if(contextObjectMap.get(ETC_VARIABLE_MAP_KEY) != null) {
            Map<String, Object> etcMap = (Map<String, Object>) contextObjectMap.get(ETC_VARIABLE_MAP_KEY);
            /* etcPattern 기준 2차 치환 */
            Matcher etcMatcher = ETC_PATTERN.matcher(resolvedTemplateText);

            StringBuilder etcResolveSb = new StringBuilder();
            while (etcMatcher.find()) {
                String objectKey = etcMatcher.group(1);  // authCode

                String variableString = etcMap.get(objectKey) == null ? "" : etcMap.get(objectKey).toString();

                etcMatcher.appendReplacement(etcResolveSb, Matcher.quoteReplacement(variableString));
            }
            etcMatcher.appendTail(etcResolveSb);

            resolvedTemplateText = etcResolveSb.toString();

        }

        return resolvedTemplateText;
    }
}
