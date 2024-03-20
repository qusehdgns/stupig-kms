package com.stupig.kms.common.cache;

import com.stupig.kms.common.utils.SessionUtils;
import com.stupig.kms.common.vo.CommonCacheVO;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;


public class UserCacheResource {

    private final ConcurrentHashMap<Long, CommonCacheVO> userCacheMap = new ConcurrentHashMap<>();

    private final long holdingTime = 10L;

    public void putUserCache(Long userSeq) {
        this.userCacheMap.put(userSeq, new CommonCacheVO(
                SessionUtils.getSessionId(),
                LocalDateTime.now().plusMinutes(this.holdingTime)
        ));
    }

    public CommonCacheVO getUserCache(Long userSeq) {
        return this.userCacheMap.get(userSeq);
    }

    public void removeUserCache(Long userSeq) {
        this.userCacheMap.remove(userSeq);
    }


}
