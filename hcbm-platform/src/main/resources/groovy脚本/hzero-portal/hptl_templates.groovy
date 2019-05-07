package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_templates.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_templates") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_templates_s', startValue:"1")
        }
        createTable(tableName: "hptl_templates", remarks: "门户模板") {
        column(name: "template_id", type: "bigint(20)", autoIncrement: true ,   remarks: "模板ID")  {constraints(primaryKey: true)} 
        column(name: "template_code", type: "varchar(30)",  remarks: "模板编码")  {constraints(nullable:"false")}  
        column(name: "template_name", type: "varchar(255)",  remarks: "模板名称")  {constraints(nullable:"false")}  
        column(name: "template_avatar", type: "varchar(255)",  remarks: "模板缩略图")   
        column(name: "template_path", type: "varchar(50)",  remarks: "模板路径")  {constraints(nullable:"false")}  
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "启用标识")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }

   addUniqueConstraint(columnNames:"template_code",tableName:"hptl_templates",constraintName: "hptl_templates_u1")   
  }    
}