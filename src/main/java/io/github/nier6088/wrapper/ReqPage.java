package io.github.nier6088.wrapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ReqPage extends BaseReq {

    private int pageNum = 1;

    private int pageSize = 10;

    @JsonIgnore
    public <T> Page<T> getPage() {
        return Page.of(this.pageNum, this.pageSize);
    }


}
