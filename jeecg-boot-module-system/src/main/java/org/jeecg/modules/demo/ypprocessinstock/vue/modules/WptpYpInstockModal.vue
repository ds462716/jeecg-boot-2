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
            <a-form-item label="入库明细序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNum', validatorRules.instockNum]" placeholder="请输入入库明细序列号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'storeCode', validatorRules.storeCode]" placeholder="请输入仓库"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
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
            <a-form-item label="原料品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'materialName', validatorRules.materialName]" placeholder="请输入原料品名"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原料品规" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入原料品规"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原料内部批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'materialBatch', validatorRules.materialBatch]" placeholder="请输入原料内部批次"></a-input>
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
            <a-form-item label="原料产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'materialOrigin', validatorRules.materialOrigin]" placeholder="请输入原料产地"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入供应商编码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="采购负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'responsible', validatorRules.responsible]" placeholder="请输入采购负责人"></a-input>
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
            <a-form-item label="来源追溯码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'traceCode', validatorRules.traceCode]" placeholder="请输入来源追溯码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="主数据码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'mainCode', validatorRules.mainCode]" placeholder="请输入主数据码"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="饮片入库附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpYpInstockFileTable.loading"
            :columns="wptpYpInstockFileTable.columns"
            :dataSource="wptpYpInstockFileTable.dataSource"
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
    name: 'WptpYpInstockModal',
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
          instockNumber:{},
          instockNo:{},
          instockNum:{},
          storeCode:{},
          hostCode:{},
          sourceFlag:{},
          productBatch:{},
          categoryCode:{},
          categoryName:{},
          materialName:{},
          guige:{},
          materialBatch:{},
          arrivalTime:{},
          num:{},
          materialOrigin:{},
          entId:{},
          responsible:{},
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
          traceCode:{},
          deleted:{},
          mainCode:{},
        },
        refKeys: ['wptpYpInstockFile', ],
        tableKeys:['wptpYpInstockFile', ],
        activeKey: 'wptpYpInstockFile',
        // 饮片入库附表
        wptpYpInstockFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '删除标志位',
              key: 'deleted',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
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
              title: '药材入库流水号',
              key: 'mainId',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '检测图片路径',
              key: 'path',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '类型',
              key: 'type',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '文件类型',
              key: 'fileType',
              type: FormTypes.date,
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
          ]
        },
        url: {
          add: "/ypprocessinstock/wptpYpInstock/add",
          edit: "/ypprocessinstock/wptpYpInstock/edit",
          wptpYpInstockFile: {
            list: '/ypprocessinstock/wptpYpInstock/queryWptpYpInstockFileByMainId'
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
        let fieldval = pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','instockNumber','instockNo','instockNum','storeCode','hostCode','sourceFlag','productBatch','categoryCode','categoryName','materialName','guige','materialBatch','arrivalTime','num','materialOrigin','entId','responsible','checkNo','checkName','checkPurpose','checkItem','checkBasis','checkTime','checkResult','checkResponsible','enviornment','method','auditStatus','flowId','traceCode','deleted','mainCode')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpYpInstockFile.list, params, this.wptpYpInstockFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpYpInstockFileList: allValues.tablesValue[0].values,
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