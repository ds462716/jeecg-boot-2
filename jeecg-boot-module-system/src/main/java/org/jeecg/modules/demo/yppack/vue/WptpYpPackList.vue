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
      <a-button type="primary" icon="download" @click="handleExportXls('饮片包装')">导出</a-button>
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

    <wptpYpPack-modal ref="modalForm" @ok="modalFormOk"></wptpYpPack-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpYpPackModal from './modules/WptpYpPackModal'
  export default {
    name: "WptpYpPackList",
    mixins:[JeecgListMixin],
    components: {
      WptpYpPackModal
    },
    data () {
      return {
        description: '饮片包装管理页面',
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
            title:'主机代码',
            align:"center",
            dataIndex: 'hostCode'
          },
          {
            title:'饮片包装流水号',
            align:"center",
            dataIndex: 'packNo'
          },
          {
            title:'包装单号',
            align:"center",
            dataIndex: 'packBatch'
          },
          {
            title:'包装明细序列号',
            align:"center",
            dataIndex: 'packNumber'
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
            title:'生产流水号',
            align:"center",
            dataIndex: 'processNo'
          },
          {
            title:'饮片名称',
            align:"center",
            dataIndex: 'piecesName'
          },
          {
            title:'产品批号',
            align:"center",
            dataIndex: 'productBatch'
          },
          {
            title:'饮片规格',
            align:"center",
            dataIndex: 'guige'
          },
          {
            title:'主数据码',
            align:"center",
            dataIndex: 'mainCode'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'计量单位',
            align:"center",
            dataIndex: 'unit'
          },
          {
            title:'包装规格',
            align:"center",
            dataIndex: 'packGuige'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/yppack/wptpYpPack/list",
          delete: "/yppack/wptpYpPack/delete",
          deleteBatch: "/yppack/wptpYpPack/deleteBatch",
          exportXlsUrl: "/yppack/wptpYpPack/exportXls",
          importExcelUrl: "yppack/wptpYpPack/importExcel",
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