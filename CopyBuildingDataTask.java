package com.longhu.du.task;

import com.longhu.du.entity.CrmBuilding;
import com.longhu.du.service.CrmDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwb on 2017/9/18.
 * 拷贝数据
 */
@Component
public class CopyBuildingDataTask {
    @Resource
    private CrmDataService crmDataService;
    public static final int BATCH_ROW = 500;
    public static List batchPagePack(List lists, int index){
        if (lists == null || lists.size() == 0) {
            return null;
        }
        int begin = index * BATCH_ROW;
        int end = lists.size() >= ((index + 1) * BATCH_ROW) ? (index + 1) *
                BATCH_ROW : lists.size();
        if (end - begin < 1) {
            return null;
        }
        List datas = lists.subList(begin, end);
        if (datas != null && datas.size() > 0) {
            return datas;
        } else {
            return null;
        }
    }

    //@Scheduled(cron = "0 0 0 * * ?")
    public void run() throws Exception {
        Map<String, Object> emptyMap = new HashMap<>();
        List<CrmBuilding> buildDatas = crmDataService.getBuildData(emptyMap);
        for (int i = 0; i < buildDatas.size()/BATCH_ROW; i++) {
            List<CrmBuilding> batList = batchPagePack(buildDatas, i);
            if (batList == null) {
                break;
            }
//            crmDataService.insertBatch(batList);
        }
    }
}
