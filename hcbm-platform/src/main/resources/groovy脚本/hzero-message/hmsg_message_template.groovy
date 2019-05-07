package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_message_template.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_message_template") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_message_template_s', startValue:"1")
        }
        createTable(tableName: "hmsg_message_template", remarks: "消息模板") {
            column(name: "template_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "template_code", type: "varchar(" + 60 * weight + ")",  remarks: "模板代码")  {constraints(nullable:"false")}  
            column(name: "template_name", type: "varchar(" + 240 * weight + ")",  remarks: "模板名称")  {constraints(nullable:"false")}  
            column(name: "template_title", type: "varchar(" + 480 * weight + ")",  remarks: "模板标题")  {constraints(nullable:"false")}  
            column(name: "template_content", type: "longtext",  remarks: "模板内容")  {constraints(nullable:"false")}  
            column(name: "message_category_code", type: "varchar(" + 30 * weight + ")",  remarks: "消息类别，值集:HMSG.MESSAGE_CATEGORY")   
            column(name: "message_subcategory_code", type: "varchar(" + 30 * weight + ")",  remarks: "消息子类型，值集:HMSG.MESSAGE_SUBCATEGORY")   
            column(name: "external_code", type: "varchar(" + 30 * weight + ")",  remarks: "短信非空，外部代码")   
            column(name: "sql_value", type: "longtext",  remarks: "取值SQL")
            column(name: "lang", type: "varchar(" + 30 * weight + ")",  remarks: "语言code")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"tenant_id,template_code,lang",tableName:"hmsg_message_template",constraintName: "hmsg_mt_ttclse_uindex")
    }
}