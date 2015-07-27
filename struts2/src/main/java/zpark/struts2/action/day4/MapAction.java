package zpark.struts2.action.day4;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapAction {
    
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    
    public String execute() {
        map = new LinkedHashMap<String, String>();

        map.put("1", "北京");
        map.put("2", "上海");
        map.put("3", "重庆");
        map.put("4", "广州");
        return "success";
    }

}
