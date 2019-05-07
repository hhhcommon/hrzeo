package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_template_server_line.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_template_server_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_template_server_line_s', startValue:"1")
        }
        createTable(tableName: "hmsg_template_server_line", remarks: "消息模板账户关联明细") {
            column(name: "temp_server_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "temp_server_id", type: "bigint(20)",  remarks: "消息模板账户,hmsg_template_server.temp_server_id")  {constraints(nullable:"false")}  
            column(name: "type_code", type: "varchar(" + 30 * weight + ")",  remarks: "模版类型，值集:HMSG.MESSAGE_TYPE")  {constraints(nullable:"false")}  
            column(name: "template_code", type: "varchar(" + 60 * weight + ")",  remarks: "消息模板编码，hmsg_message_template.template_code")  {constraints(nullable:"false")}  
            column(name: "server_id", type: "bigint(20)",  remarks: "服务ID,hmsg_email_server或hmsg_sms_server")   
            column(name: "remark", type: "varchar(" + 480 * weight + ")",  remarks: "备注说明")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"temp_server_id,type_code",tableName:"hmsg_template_server_line",constraintName: "hmsg_template_server_line_u1")
    }
}