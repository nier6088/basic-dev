package io.github.nier6088.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.nier6088.constants.DbStatusConstant;
import io.github.nier6088.util.QueryDataUtil;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseEntity> extends IService<T> {

    default LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    default LambdaUpdateWrapper<T> getLambdaUpdateWrapper() {
        return Wrappers.lambdaUpdate();
    }


    default boolean existRecordInfo(SFunction<T, ?> function1, Object value) {

        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return this.count(lambdaQueryWrapper) > 0;
    }

    default boolean existRecordInfo(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return this.count(lambdaQueryWrapper) > 0;
    }

    default boolean existRecordInfo(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2, SFunction<T, ?> function3, Object value3) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return this.count(lambdaQueryWrapper) > 0;
    }

    default Integer countByCondition(SFunction<T, ?> function1, Object value1) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return Math.toIntExact(this.count(lambdaQueryWrapper));
    }

    default Integer countByCondition(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return Math.toIntExact(this.count(lambdaQueryWrapper));
    }

    default Integer countByCondition(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2, SFunction<T, ?> function3, Object value3) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.eq(function3, value3);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        return Math.toIntExact(this.count(lambdaQueryWrapper));
    }

    default Map<Long, Integer> countByConditionGroup(SFunction<T, ?> function1, List<Long> value) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = getLambdaQueryWrapper();
        lambdaQueryWrapper.in(function1, value);
        lambdaQueryWrapper.eq(T::getRecordStatus, DbStatusConstant.RECORD_STATUS_NORMAL);
        List list = this.list(lambdaQueryWrapper);
        return QueryDataUtil.listToGroupByCountMap(list, function1);
    }

    default boolean removeRecordInfo(SFunction<T, ?> function1, Object value) {
        LambdaUpdateWrapper<T> lambdaQueryWrapper = getLambdaUpdateWrapper();
        lambdaQueryWrapper.eq(function1, value);
        lambdaQueryWrapper.setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
        return this.update(lambdaQueryWrapper);
    }

    default boolean removeById(Long id) {
        LambdaUpdateWrapper<T> lambdaQueryWrapper = getLambdaUpdateWrapper();
        lambdaQueryWrapper.eq(T::getId, id);
        lambdaQueryWrapper.setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
        return this.update(lambdaQueryWrapper);
    }

    default boolean removeRecordInfo(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2) {
        LambdaUpdateWrapper<T> lambdaQueryWrapper = getLambdaUpdateWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
        return this.update(lambdaQueryWrapper);
    }

    default boolean removeRecordInfo(SFunction<T, ?> function1, Object value1, SFunction<T, ?> function2, Object value2, SFunction<T, ?> function3, Object value3) {
        LambdaUpdateWrapper<T> lambdaQueryWrapper = getLambdaUpdateWrapper();
        lambdaQueryWrapper.eq(function1, value1);
        lambdaQueryWrapper.eq(function2, value2);
        lambdaQueryWrapper.eq(function3, value3);
        lambdaQueryWrapper.setSql("record_status = " + DbStatusConstant.RECORD_STATUS_ABNORMAL);
        return this.update(lambdaQueryWrapper);
    }


}
