<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="仓库编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'storeCode', validatorRules.storeCode]" placeholder="请输入仓库编码"></a-input>
        </a-form-item>
        <a-form-item label="仓库名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入仓库名称"></a-input>
        </a-form-item>
        <a-form-item label="省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省"></a-input>
        </a-form-item>
        <a-form-item label="市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市"></a-input>
        </a-form-item>
        <a-form-item label="区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'district', validatorRules.district]" placeholder="请输入区"></a-input>
        </a-form-item>
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
        </a-form-item>
        <a-form-item label="gps经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gpsLongitude', validatorRules.gpsLongitude]" placeholder="请输入gps经度"></a-input>
        </a-form-item>
        <a-form-item label="gps纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gpsLatitude', validatorRules.gpsLatitude]" placeholder="请输入gps纬度"></a-input>
        </a-form-item>
        <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'entId', validatorRules.entId]" placeholder="请输入所属企业"></a-input>
        </a-form-item>
        <a-form-item label="所属基地" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'baseCode', validatorRules.baseCode]" placeholder="请输入所属基地"></a-input>
        </a-form-item>
        <a-form-item label="审核标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'auditStatus', validatorRules.auditStatus]" placeholder="请输入审核标志"></a-input>
        </a-form-item>
        <a-form-item label="删除标志 " :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'deleted', validatorRules.deleted]" placeholder="请输入删除标志 "></a-input>
        </a-form-item>
        <a-form-item label="工作流" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'flowId', validatorRules.flowId]" placeholder="请输入工作流"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "WptpStorageModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        storeCode:{},
        name:{},
        province:{},
        city:{},
        district:{},
        address:{},
        gpsLongitude:{},
        gpsLatitude:{},
        entId:{},
        baseCode:{},
        auditStatus:{},
        deleted:{},
        flowId:{},
        },
        url: {
          add: "/storageinfo/wptpStorage/add",
          edit: "/storageinfo/wptpStorage/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'storeCode','name','province','city','district','address','gpsLongitude','gpsLatitude','entId','baseCode','auditStatus','deleted','flowId'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'storeCode','name','province','city','district','address','gpsLongitude','gpsLatitude','entId','baseCode','auditStatus','deleted','flowId'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>