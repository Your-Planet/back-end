package kr.co.yourplanet.core.alimtalk.support;

import static org.springframework.util.StringUtils.*;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlimTalkVariableResolver {

    private AlimTalkVariableResolver() {}

    /**
     * 알림톡 템플릿 문구 내에 포함된 #{객체.필드} 형태의 변수를 실제 객체의 필드 값으로 치환합니다.
     *
     * 예시:
     *   templateText = "안녕하세요 #{member.name}님, 프로젝트 번호는 #{project.id}입니다."
     *   contextObjectMap = Map.of("member", MemberEntity, "project", ProjectEntity)
     *
     * 위와 같은 경우 MemberEntity.getName(), ProjectEntity.getId() 를 리플렉션으로 호출해
     * 템플릿 문자열 내 변수에 실제 값을 대입합니다.
     *
     * @param templateText #{도메인.필드} 형식의 변수가 포함된 템플릿 문구
     * @param contextObjectMap 템플릿에서 참조할 도메인 객체를 담은 맵 (예: "member" → member 객체)
     * @return 실제 값이 치환된 문자열
     */
    public static String resolve(String templateText, Map<String, Object> contextObjectMap) {
        Pattern pattern = Pattern.compile("#\\{(\\w+)\\.(\\w+)}"); // ex #{member.name}
        Matcher matcher = pattern.matcher(templateText);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String objectKey = matcher.group(1);  // member
            String fieldKey = matcher.group(2);   // name

            Object targetObject = contextObjectMap.get(objectKey);
            String replacement = "";

            if (targetObject != null) {
                try {
                    Method getter = targetObject.getClass().getMethod("get" + capitalize(fieldKey));
                    Object value = getter.invoke(targetObject);
                    replacement = value != null ? value.toString() : "";
                } catch (Exception e) {
                    replacement = "";
                }
            }

            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
