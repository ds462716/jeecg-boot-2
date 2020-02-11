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

        <a-form-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createName', validatorRules.createName]" placeholder="请输入创建人名称"></a-input>
        </a-form-item>
        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="[ 'createDate', validatorRules.createDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateName', validatorRules.updateName]" placeholder="请输入更新人名称"></a-input>
        </a-form-item>
        <a-form-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人登录名称"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="[ 'updateDate', validatorRules.updateDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="主机代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hostCode', validatorRules.hostCode]" placeholder="请输入主机代码"></a-input>
        </a-form-item>
        <a-form-item label="饮片包装流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'packNo', validatorRules.packNo]" placeholder="请输入饮片包装流水号"></a-input>
        </a-form-item>
        <a-form-item label="包装单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'packBatch', validatorRules.packBatch]" placeholder="请输入包装单号"></a-input>
        </a-form-item>
        <a-form-item label="包装明细序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'packNumber', validatorRules.packNumber]" placeholder="请输入包装明细序列号"></a-input>
        </a-form-item>
        <a-form-item label="种类编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'categoryCode', validatorRules.categoryCode]" placeholder="请输入种类编码"></a-input>
        </a-form-item>
        <a-form-item label="种类名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'categoryName', validatorRules.categoryName]" placeholder="请输入种类名称"></a-input>
        </a-form-item>
        <a-form-item label="生产流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'processNo', validatorRules.processNo]" placeholder="请输入生产流水号"></a-input>
        </a-form-item>
        <a-form-item label="饮片名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'piecesName', validatorRules.piecesName]" placeholder="请输入饮片名称"></a-input>
        </a-form-item>
        <a-form-item label="产品批号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'productBatch', validatorRules.productBatch]" placeholder="请输入产品批号"></a-input>
        </a-form-item>
        <a-form-item label="饮片规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'guige', validatorRules.guige]" placeholder="请输入饮片规格"></a-input>
        </a-form-item>
        <a-form-item label="主数据码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mainCode', validatorRules.mainCode]" placeholder="请输入主数据码"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'number', validatorRules.number]" placeholder="请输入数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="计量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入计量单位"></a-input>
        </a-form-item>
        <a-form-item label="包装规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'packGuige', validatorRules.packGuige]" placeholder="请输入包装规格"></a-input>
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
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "WptpYpPackModal",
    components: { 
      JDate,
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
        createName:{},
        createBy:{},
        createDate:{},
        updateName:{},
        updateBy:{},
        updateDate:{},
        hostCode:{},
        packNo:{},
        packBatch:{},
        packNumber:{},
        categoryCode:{},
        categoryName:{},
        processNo:{},
        piecesName:{},
        productBatch:{},
        guige:{},
        mainCode:{},
        number:{},
        unit:{},
        packGuige:{},
        },
        url: {
          add: "/yppack/wptpYpPack/add",
          edit: "/yppack/wptpYpPack/edit",
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
          this.form.setFieldsValue(pick(this.model,'createName','createBy','createDate','updateName','updateBy','updateDate','hostCode','packNo','packBatch','packNumber','categoryCode','categoryName','processNo','piecesName','productBatch','guige','mainCode','number','unit','packGuige'))
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
        this.form.setFieldsValue(pick(row,'createName','createBy','createDate','updateName','updateBy','updateDate','hostCode','packNo','packBatch','packNumber','categoryCode','categoryName','processNo','piecesName','productBatch','guige','mainCode','number','unit','packGuige'))
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