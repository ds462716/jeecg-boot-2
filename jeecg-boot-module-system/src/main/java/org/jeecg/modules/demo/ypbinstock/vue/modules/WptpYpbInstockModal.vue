<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'createName', validatorRules.createName]" placeholder="请输入创建人名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人登录名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建日期" v-decorator="[ 'createDate', validatorRules.createDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'updateName', validatorRules.updateName]" placeholder="请输入更新人名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人登录名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新日期" v-decorator="[ 'updateDate', validatorRules.updateDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="饮片入库流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNo', validatorRules.instockNo]" placeholder="请输入饮片入库流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="种类编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categoryCode', validatorRules.categoryCode]" placeholder="请输入种类编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="种类名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'categoryName', validatorRules.categoryName]" placeholder="请输入种类名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="主数据码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'mainCode', validatorRules.mainCode]" placeholder="请输入主数据码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="饮片名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ypName', validatorRules.ypName]" placeholder="请输入饮片名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="饮片规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入饮片规格"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="包装规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'packGuige', validatorRules.packGuige]" placeholder="请输入包装规格"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="来源追溯码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'traceCode', validatorRules.traceCode]" placeholder="请输入来源追溯码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="来源环节" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'sourceFlag', validatorRules.sourceFlag]" placeholder="请输入来源环节"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="产品批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'productBatch', validatorRules.productBatch]" placeholder="请输入产品批号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'arrivalTime', validatorRules.arrivalTime]" placeholder="请输入到货时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采购数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'num', validatorRules.num]" placeholder="请输入采购数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'producedDate', validatorRules.producedDate]" placeholder="请输入生产日期"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="有效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'expiredDate', validatorRules.expiredDate]" placeholder="请输入有效日期"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'medicinalOrigin', validatorRules.medicinalOrigin]" placeholder="请输入药材产地"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="生产厂商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'manufacturer', validatorRules.manufacturer]" placeholder="请输入生产厂商"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="供应商企业编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入供应商企业编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采购负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'purchaseResponsible', validatorRules.purchaseResponsible]" placeholder="请输入采购负责人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检验依据" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkBasis', validatorRules.checkBasis]" placeholder="请输入检验依据"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="储存环境" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'enviornment', validatorRules.enviornment]" placeholder="请输入储存环境"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="养护方法" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'method', validatorRules.method]" placeholder="请输入养护方法"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="工作流" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="饮片经营药材入库附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpYpbInstockFileTable.loading"
            :columns="wptpYpbInstockFileTable.columns"
            :dataSource="wptpYpbInstockFileTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'WptpYpbInstockModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          createName:{},
          createBy:{},
          createDate:{},
          updateName:{},
          updateBy:{},
          updateDate:{},
          instockNo:{},
          categoryCode:{},
          categoryName:{},
          mainCode:{},
          ypName:{},
          guige:{},
          packGuige:{},
          traceCode:{},
          sourceFlag:{},
          productBatch:{},
          arrivalTime:{},
          num:{},
          producedDate:{},
          expiredDate:{},
          medicinalOrigin:{},
          manufacturer:{},
          entId:{},
          purchaseResponsible:{},
          checkBasis:{},
          enviornment:{},
          method:{},
          auditStatus:{},
          flowId:{},
          deleted:{},
          hostCode:{},
        },
        refKeys: ['wptpYpbInstockFile', ],
        tableKeys:['wptpYpbInstockFile', ],
        activeKey: 'wptpYpbInstockFile',
        // 饮片经营药材入库附表
        wptpYpbInstockFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '创建人名称',
              key: 'createName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '创建人登录名称',
              key: 'createBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '创建日期',
              key: 'createDate',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新人名称',
              key: 'updateName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新人登录名称',
              key: 'updateBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '饮片入库流水号',
              key: 'mainId',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '路径',
              key: 'path',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '类型',
              key: 'type',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '删除标志',
              key: 'deleted',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '文件类型',
              key: 'fileType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/ypbinstock/wptpYpbInstock/add",
          edit: "/ypbinstock/wptpYpbInstock/edit",
          wptpYpbInstockFile: {
            list: '/ypbinstock/wptpYpbInstock/queryWptpYpbInstockFileByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','instockNo','categoryCode','categoryName','mainCode','ypName','guige','packGuige','traceCode','sourceFlag','productBatch','arrivalTime','num','producedDate','expiredDate','medicinalOrigin','manufacturer','entId','purchaseResponsible','checkBasis','enviornment','method','auditStatus','flowId','deleted','hostCode')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpYpbInstockFile.list, params, this.wptpYpbInstockFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpYpbInstockFileList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      }
      
      
    }
  }
</script>

<style scoped>
</style>