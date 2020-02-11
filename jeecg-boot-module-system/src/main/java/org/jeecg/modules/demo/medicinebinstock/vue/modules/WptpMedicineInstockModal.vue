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
            <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材入库流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNumber', validatorRules.instockNumber]" placeholder="请输入药材入库流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="入库单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNo', validatorRules.instockNo]" placeholder="请输入入库单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'storeCode', validatorRules.storeCode]" placeholder="请输入仓库"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原入库单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'oriInstockNo', validatorRules.oriInstockNo]" placeholder="请输入原入库单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'oriStoreCode', validatorRules.oriStoreCode]" placeholder="请输入原仓库"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="来源追溯码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'traceCode', validatorRules.traceCode]" placeholder="请输入来源追溯码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'medicineName', validatorRules.medicineName]" placeholder="请输入药材品名"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入药材规格"></a-input>
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
            <a-form-item label="产品批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'productBatch', validatorRules.productBatch]" placeholder="请输入产品批号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="批准文号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'approvalNumber', validatorRules.approvalNumber]" placeholder="请输入批准文号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="进口批件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'importApproval', validatorRules.importApproval]" placeholder="请输入进口批件"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'arriveTime', validatorRules.arriveTime]" placeholder="请输入到货时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采购数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'purchaseNum', validatorRules.purchaseNum]" placeholder="请输入采购数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'medicinalOrigin', validatorRules.medicinalOrigin]" placeholder="请输入药材产地"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入供应商编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采购负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'purchaseLeader', validatorRules.purchaseLeader]" placeholder="请输入采购负责人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="质检单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkNo', validatorRules.checkNo]" placeholder="请输入质检单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkName', validatorRules.checkName]" placeholder="请输入检品名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检验目的" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkPurpose', validatorRules.checkPurpose]" placeholder="请输入检验目的"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检验项目及结论" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkItem', validatorRules.checkItem]" placeholder="请输入检验项目及结论"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检测依据" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkBasis', validatorRules.checkBasis]" placeholder="请输入检测依据"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检测时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkTime', validatorRules.checkTime]" placeholder="请输入检测时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="检测结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkResult', validatorRules.checkResult]" placeholder="请输入检测结果"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="质检负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkResponsible', validatorRules.checkResponsible]" placeholder="请输入质检负责人"></a-input>
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
            <a-form-item label="入库明细序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNum', validatorRules.instockNum]" placeholder="请输入入库明细序列号"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="药材入库附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpMedicineInstockFileTable.loading"
            :columns="wptpMedicineInstockFileTable.columns"
            :dataSource="wptpMedicineInstockFileTable.dataSource"
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

  export default {
    name: 'WptpMedicineInstockModal',
    mixins: [JEditableTableMixin],
    components: {
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
          hostCode:{},
          instockNumber:{},
          instockNo:{},
          storeCode:{},
          oriInstockNo:{},
          oriStoreCode:{},
          traceCode:{},
          medicineName:{},
          guige:{},
          categoryCode:{},
          categoryName:{},
          mainCode:{},
          productBatch:{},
          approvalNumber:{},
          importApproval:{},
          arriveTime:{},
          purchaseNum:{},
          medicinalOrigin:{},
          entId:{},
          purchaseLeader:{},
          checkNo:{},
          checkName:{},
          checkPurpose:{},
          checkItem:{},
          checkBasis:{},
          checkTime:{},
          checkResult:{},
          checkResponsible:{},
          enviornment:{},
          method:{},
          auditStatus:{},
          flowId:{},
          deleted:{},
          instockNum:{},
        },
        refKeys: ['wptpMedicineInstockFile', ],
        tableKeys:['wptpMedicineInstockFile', ],
        activeKey: 'wptpMedicineInstockFile',
        // 药材入库附表
        wptpMedicineInstockFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '药材入库流水号',
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
              title: '删除标志',
              key: 'deleted',
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
              title: '文件类型',
              key: 'fileType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/medicinebinstock/wptpMedicineInstock/add",
          edit: "/medicinebinstock/wptpMedicineInstock/edit",
          wptpMedicineInstockFile: {
            list: '/medicinebinstock/wptpMedicineInstock/queryWptpMedicineInstockFileByMainId'
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
        let fieldval = pick(this.model,'hostCode','instockNumber','instockNo','storeCode','oriInstockNo','oriStoreCode','traceCode','medicineName','guige','categoryCode','categoryName','mainCode','productBatch','approvalNumber','importApproval','arriveTime','purchaseNum','medicinalOrigin','entId','purchaseLeader','checkNo','checkName','checkPurpose','checkItem','checkBasis','checkTime','checkResult','checkResponsible','enviornment','method','auditStatus','flowId','deleted','instockNum')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpMedicineInstockFile.list, params, this.wptpMedicineInstockFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpMedicineInstockFileList: allValues.tablesValue[0].values,
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