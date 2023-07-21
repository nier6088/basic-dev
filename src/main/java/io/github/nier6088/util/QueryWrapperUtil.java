package io.github.nier6088.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.nier6088.constants.DbStatusConstant;


public class QueryWrapperUtil {
    public static <T> QueryWrapper<T> build() {
        return new QueryWrapper<T>();
    }

    public static <T> UpdateWrapper<T> removeById(Long activeId) {
        return Wrappers.<T>update().eq("id", activeId).set("record_status", DbStatusConstant.RECORD_STATUS_ABNORMAL);
    }

    public static <T> LambdaUpdateWrapper<T> remove(SFunction<T, ?> sFunction, Long activeId) {
        return Wrappers.<T>lambdaUpdate().eq(sFunction, activeId).setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
    }

    public static <T> LambdaUpdateWrapper<T> removeByUserId(SFunction<T, ?> f1, Long userId, SFunction<T, ?> sFunction, Long activeId) {
        return Wrappers.<T>lambdaUpdate().eq(f1, userId).eq(sFunction, activeId).setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
    }
}
