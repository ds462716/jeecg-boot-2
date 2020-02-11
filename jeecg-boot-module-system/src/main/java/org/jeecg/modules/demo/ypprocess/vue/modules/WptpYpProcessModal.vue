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
            <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="生产流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processNo', validatorRules.processNo]" placeholder="请输入生产流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="饮片品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ypName', validatorRules.ypName]" placeholder="请输入饮片品名"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="饮片规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'ypGuige', validatorRules.ypGuige]" placeholder="请输入饮片规格"></a-input>
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
            <a-form-item label="生产批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'productBatch', validatorRules.productBatch]" placeholder="请输入生产批号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原料来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'source', validatorRules.source]" placeholder="请输入原料来源"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原生产流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'oriProcessNo', validatorRules.oriProcessNo]" placeholder="请输入原生产流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="药材入库流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNo', validatorRules.instockNo]" placeholder="请输入药材入库流水号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原料数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'materialNum', validatorRules.materialNum]" placeholder="请输入原料数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="原料内部批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'instockNumber', validatorRules.instockNumber]" placeholder="请输入原料内部批号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="成品数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'productNum', validatorRules.productNum]" placeholder="请输入成品数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入单位"></a-input>
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
            <a-form-item label="加工地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processPlace', validatorRules.processPlace]" placeholder="请输入加工地点"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'processResponsible', validatorRules.processResponsible]" placeholder="请输入负责人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="入库时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'inTime', validatorRules.inTime]" placeholder="请输入入库时间"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="质检单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'qualCheckNum', validatorRules.qualCheckNum]" placeholder="请输入质检单号"></a-input>
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
            <a-form-item label="检测时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'checkTime', validatorRules.checkTime]" placeholder="请输入检测时间"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="饮片加工附表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpYpProcessFileTable.loading"
            :columns="wptpYpProcessFileTable.columns"
            :dataSource="wptpYpProcessFileTable.dataSource"
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
    name: 'WptpYpProcessModal',
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
          hostCode:{},
          processNo:{},
          ypName:{},
          ypGuige:{},
          categoryCode:{},
          categoryName:{},
          mainCode:{},
          productBatch:{},
          source:{},
          oriProcessNo:{},
          instockNo:{},
          materialNum:{},
          instockNumber:{},
          productNum:{},
          unit:{},
          producedDate:{},
          expiredDate:{},
          processPlace:{},
          processResponsible:{},
          inTime:{},
          qualCheckNum:{},
          checkName:{},
          checkPurpose:{},
          checkItem:{},
          checkBasis:{},
          checkResult:{},
          checkResponsible:{},
          auditStatus:{},
          flowId:{},
          deleted:{},
          checkTime:{},
        },
        refKeys: ['wptpYpProcessFile', ],
        tableKeys:['wptpYpProcessFile', ],
        activeKey: 'wptpYpProcessFile',
        // 饮片加工附表
        wptpYpProcessFileTable: {
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
              title: '生产流水号',
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
          add: "/ypprocess/wptpYpProcess/add",
          edit: "/ypprocess/wptpYpProcess/edit",
          wptpYpProcessFile: {
            list: '/ypprocess/wptpYpProcess/queryWptpYpProcessFileByMainId'
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
        let fieldval = pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','hostCode','processNo','ypName','ypGuige','categoryCode','categoryName','mainCode','productBatch','source','oriProcessNo','instockNo','materialNum','instockNumber','productNum','unit','producedDate','expiredDate','processPlace','processResponsible','inTime','qualCheckNum','checkName','checkPurpose','checkItem','checkBasis','checkResult','checkResponsible','auditStatus','flowId','deleted','checkTime')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpYpProcessFile.list, params, this.wptpYpProcessFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpYpProcessFileList: allValues.tablesValue[0].values,
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