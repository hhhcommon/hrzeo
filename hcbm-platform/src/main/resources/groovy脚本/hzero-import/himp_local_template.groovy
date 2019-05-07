package script.db

databaseChangeLog(logicalFilePath: 'script/db/himp_local_template.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-02-19-himp_local_template") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'himp_local_template_s', startValue:"1")
        }
        createTable(tableName: "himp_local_template", remarks: "") {
            column(name: "id", type: "bigint(20)", autoIncrement: true ,   remarks: "主键")  {constraints(primaryKey: true)} 
            column(name: "template_code", type: "varchar(" + 30 * weight + ")",  remarks: "模板编码")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID,hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "template_json", type: "longtext",  remarks: "模板JSON内容")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",  remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",  remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"template_code,tenant_id",tableName:"himp_local_template",constraintName: "himp_local_template_u1")
    }
}