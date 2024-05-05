package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.domain.MemberInstagramInfo;
import kr.co.yourplanet.batch.repository.InstagramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@RequiredArgsConstructor
@Component
public class InstagramBatchReader implements ItemReader<MemberInstagramInfo> {

    private final InstagramRepository instagramRepository;
    private Iterator<MemberInstagramInfo> memberInstagramInfoIterator;

    @Override
    public MemberInstagramInfo read() {
        // 데이터 리스트를 초기화하고 iterator를 얻는다
        if (memberInstagramInfoIterator == null) {
            List<MemberInstagramInfo> memberInstagramInfoList = instagramRepository.findAllMemberInstagramInfo();
            memberInstagramInfoIterator = memberInstagramInfoList.iterator();
        }

        return memberInstagramInfoIterator.hasNext() ? memberInstagramInfoIterator.next() : null;
    }
}
