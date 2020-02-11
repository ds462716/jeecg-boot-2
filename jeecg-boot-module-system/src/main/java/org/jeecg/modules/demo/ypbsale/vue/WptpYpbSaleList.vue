<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('饮片经营销售')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <wptpYpbSale-modal ref="modalForm" @ok="modalFormOk"></wptpYpbSale-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpYpbSaleModal from './modules/WptpYpbSaleModal'
  export default {
    name: "WptpYpbSaleList",
    mixins:[JeecgListMixin],
    components: {
      WptpYpbSaleModal
    },
    data () {
      return {
        description: '饮片经营销售管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'创建人名称',
            align:"center",
            dataIndex: 'createName'
          },
          {
            title:'创建人登录名称',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'更新人名称',
            align:"center",
            dataIndex: 'updateName'
          },
          {
            title:'更新人登录名称',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'更新日期',
            align:"center",
            dataIndex: 'updateDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'饮片销售流水号',
            align:"center",
            dataIndex: 'saleNo'
          },
          {
            title:'追溯码',
            align:"center",
            dataIndex: 'traceCode'
          },
          {
            title:'协会追溯码',
            align:"center",
            dataIndex: 'xhTraceCode'
          },
          {
            title:'种类编码',
            align:"center",
            dataIndex: 'categoryCode'
          },
          {
            title:'种类名称',
            align:"center",
            dataIndex: 'categoryName'
          },
          {
            title:'饮片名称',
            align:"center",
            dataIndex: 'ypName'
          },
          {
            title:'饮片规格',
            align:"center",
            dataIndex: 'ypGuige'
          },
          {
            title:'饮片入库流水号',
            align:"center",
            dataIndex: 'instockNo'
          },
          {
            title:'销售时间',
            align:"center",
            dataIndex: 'saleTime'
          },
          {
            title:'销售数量',
            align:"center",
            dataIndex: 'num'
          },
          {
            title:'出库时间',
            align:"center",
            dataIndex: 'outTime'
          },
          {
            title:'产品批号',
            align:"center",
            dataIndex: 'productBatch'
          },
          {
            title:'客户',
            align:"center",
            dataIndex: 'customer'
          },
          {
            title:'收货地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'审核标志',
            align:"center",
            dataIndex: 'auditStatus'
          },
          {
            title:'工作流',
            align:"center",
            dataIndex: 'flowId'
          },
          {
            title:'删除标志',
            align:"center",
            dataIndex: 'deleted'
          },
          {
            title:'主机代码',
            align:"center",
            dataIndex: 'hostCode'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/ypbsale/wptpYpbSale/list",
          delete: "/ypbsale/wptpYpbSale/delete",
          deleteBatch: "/ypbsale/wptpYpbSale/deleteBatch",
          exportXlsUrl: "/ypbsale/wptpYpbSale/exportXls",
          importExcelUrl: "ypbsale/wptpYpbSale/importExcel",
        },
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>