package script.db

databaseChangeLog(logicalFilePath: 'script/db/himp_template_target.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-02-19-himp_template_target") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'himp_template_target_s', startValue:"1")
        }
        createTable(tableName: "himp_template_target", remarks: "导入目标") {
            column(name: "id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "header_id", type: "bigint(20)",  remarks: "模板头ID,himp_template_header.id")  {constraints(nullable:"false")}  
            column(name: "sheet_index", type: "int(11)",   defaultValue:"0",   remarks: "sheet页序号")  {constraints(nullable:"false")}  
            column(name: "sheet_name", type: "varchar(" + 30 * weight + ")",  remarks: "Sheet页名称")  {constraints(nullable:"false")}  
            column(name: "datasource_id", type: "bigint(20)",  remarks: "数据源，himp_datasource.datasource_id")   
            column(name: "table_name", type: "varchar(" + 60 * weight + ")",  remarks: "正式数据表名")   
            column(name: "rule_script_code", type: "varchar(" + 30 * weight + ")",  remarks: "脚本编码,hpfm_rule_script.script_code")   
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"header_id,sheet_index",tableName:"himp_template_target",constraintName: "himp_template_target_u1")
    }
}