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
            <a-form-item label="饮片销售水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'saleNumber', validatorRules.saleNumber]" placeholder="请输入饮片销售水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'saleNo', validatorRules.saleNo]" placeholder="请输入销售单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="销售明细序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'saleNum', validatorRules.saleNum]" placeholder="请输入销售明细序列号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="追溯码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'traceCode', validatorRules.traceCode]" placeholder="请输入追溯码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="产品来源流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'sourceNo', validatorRules.sourceNo]" placeholder="请输入产品来源流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="产品来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'source', validatorRules.source]" placeholder="请输入产品来源"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="产品批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'batchNo', validatorRules.batchNo]" placeholder="请输入产品批号"></a-input>
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
            <a-form-item label="产品规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入产品规格"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="发货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'num', validatorRules.num]" placeholder="请输入发货数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="计量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入计量单位"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="包装规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'packGuige', validatorRules.packGuige]" placeholder="请输入包装规格"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="销售时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'saleTime', validatorRules.saleTime]" placeholder="请输入销售时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'medicineOrigin', validatorRules.medicineOrigin]" placeholder="请输入药材产地"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="流向单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入流向单位"></a-input>
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
        <a-tab-pane tab="饮片销售附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpYpSaleFileTable.loading"
            :columns="wptpYpSaleFileTable.columns"
            :dataSource="wptpYpSaleFileTable.dataSource"
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
    name: 'WptpYpSaleModal',
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
          saleNumber:{},
          saleNo:{},
          saleNum:{},
          traceCode:{},
          sourceNo:{},
          source:{},
          batchNo:{},
          categoryCode:{},
          categoryName:{},
          mainCode:{},
          ypName:{},
          guige:{},
          num:{},
          unit:{},
          packGuige:{},
          saleTime:{},
          medicineOrigin:{},
          entId:{},
          auditStatus:{},
          flowId:{},
          deleted:{},
          hostCode:{},
        },
        refKeys: ['wptpYpSaleFile', ],
        tableKeys:['wptpYpSaleFile', ],
        activeKey: 'wptpYpSaleFile',
        // 饮片销售附表
        wptpYpSaleFileTable: {
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
              title: '更新日期',
              key: 'updateDate',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '饮片销售流水号',
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
          add: "/ypprocesssale/wptpYpSale/add",
          edit: "/ypprocesssale/wptpYpSale/edit",
          wptpYpSaleFile: {
            list: '/ypprocesssale/wptpYpSale/queryWptpYpSaleFileByMainId'
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
        let fieldval = pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','saleNumber','saleNo','saleNum','traceCode','sourceNo','source','batchNo','categoryCode','categoryName','mainCode','ypName','guige','num','unit','packGuige','saleTime','medicineOrigin','entId','auditStatus','flowId','deleted','hostCode')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpYpSaleFile.list, params, this.wptpYpSaleFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpYpSaleFileList: allValues.tablesValue[0].values,
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