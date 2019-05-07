package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_templates_config.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_templates_config") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_templates_config_s', startValue:"1")
        }
        createTable(tableName: "hptl_templates_config", remarks: "门户模板配置") {
        column(name: "config_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
        column(name: "assign_id", type: "bigint(20)",  remarks: "模板分配ID  HPTL_PORTAL_ASSIGN.ASSIGN_ID")  {constraints(nullable:"false")}  
        column(name: "template_id", type: "bigint(20)",  remarks: "模板ID HPTL_TEMPLATES.TEMPLATE_ID")  {constraints(nullable:"false")}  
        column(name: "default_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "默认标识")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }
   createIndex(tableName: "hptl_templates_config", indexName: "hptl_templates_config_n1") {
            column(name: "template_id")
            column(name: "assign_id")
        }          

  }    
}