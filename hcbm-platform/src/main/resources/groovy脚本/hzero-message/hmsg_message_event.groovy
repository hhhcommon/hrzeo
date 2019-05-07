package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_message_event.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_message_event") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_message_event_s', startValue:"1")
        }
        createTable(tableName: "hmsg_message_event", remarks: "消息事件") {
            column(name: "message_event_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "event_id", type: "bigint(20)",  remarks: "事件ID,hpfm_event.event_id")  {constraints(nullable:"false")}  
            column(name: "event_code", type: "varchar(" + 30 * weight + ")",  remarks: "事件CODE,hpfm_event.event_code")  {constraints(nullable:"false")}  
            column(name: "receiver_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "接收者类型代码，hmsg_receiver_type.type_code")  {constraints(nullable:"false")}  
            column(name: "temp_server_id", type: "bigint(20)",  remarks: "消息模板账户ID，hmsg_template_server.temp_server_id")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}