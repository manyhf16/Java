package zpark.entity;

import javax.persistence.Entity;
import javax.persistence.TableGenerator;

@Entity
//@TableGenerator(name = "sample_tg", table = "pk_table", pkColumnName = "table_name", pkColumnValue = "sampleentity", valueColumnName = "next_value", initialValue = 0, allocationSize = 1)
public class ExecutionInfo {

    private String className;

    private String method;

    private long executeTime;

}
