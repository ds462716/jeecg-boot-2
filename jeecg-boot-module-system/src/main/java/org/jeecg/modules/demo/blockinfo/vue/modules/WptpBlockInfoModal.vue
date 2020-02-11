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
            <a-form-item label="地块编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'blockCode', validatorRules.blockCode]" placeholder="请输入地块编号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入所属企业"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'gpsLongitude', validatorRules.gpsLongitude]" placeholder="请输入经度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'gpsLatitude', validatorRules.gpsLatitude]" placeholder="请输入纬度"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="面积" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'baseArea', validatorRules.baseArea]" placeholder="请输入面积"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="删除标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="地块表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wptpBlockFileTable.loading"
            :columns="wptpBlockFileTable.columns"
            :dataSource="wptpBlockFileTable.dataSource"
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
    name: 'WptpBlockInfoModal',
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
          blockCode:{},
          entId:{},
          gpsLongitude:{},
          gpsLatitude:{},
          baseArea:{},
          deleted:{},
        },
        refKeys: ['wptpBlockFile', ],
        tableKeys:['wptpBlockFile', ],
        activeKey: 'wptpBlockFile',
        // 地块表
        wptpBlockFileTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '地块编码',
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
          add: "/blockinfo/wptpBlockInfo/add",
          edit: "/blockinfo/wptpBlockInfo/edit",
          wptpBlockFile: {
            list: '/blockinfo/wptpBlockInfo/queryWptpBlockFileByMainId'
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
        let fieldval = pick(this.model,'hostCode','blockCode','entId','gpsLongitude','gpsLatitude','baseArea','deleted')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wptpBlockFile.list, params, this.wptpBlockFileTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wptpBlockFileList: allValues.tablesValue[0].values,
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