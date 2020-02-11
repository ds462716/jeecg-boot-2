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
      <a-button type="primary" icon="download" @click="handleExportXls('企业基本信息')">导出</a-button>
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

    <wptpEntInfo-modal ref="modalForm" @ok="modalFormOk"></wptpEntInfo-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WptpEntInfoModal from './modules/WptpEntInfoModal'
  export default {
    name: "WptpEntInfoList",
    mixins:[JeecgListMixin],
    components: {
      WptpEntInfoModal
    },
    data () {
      return {
        description: '企业基本信息管理页面',
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
            title:'主机代码',
            align:"center",
            dataIndex: 'hostCode'
          },
          {
            title:'企业编码',
            align:"center",
            dataIndex: 'entId'
          },
          {
            title:'企业类别',
            align:"center",
            dataIndex: 'entType'
          },
          {
            title:'组织机构代码',
            align:"center",
            dataIndex: 'orgCode'
          },
          {
            title:'法人',
            align:"center",
            dataIndex: 'entPrincipal'
          },
          {
            title:'授权人',
            align:"center",
            dataIndex: 'authorize'
          },
          {
            title:'联系电话',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'注册资金',
            align:"center",
            dataIndex: 'regCapi'
          },
          {
            title:'工商注册号',
            align:"center",
            dataIndex: 'busRegisNum'
          },
          {
            title:'企业地址',
            align:"center",
            dataIndex: 'entAdd'
          },
          {
            title:'成立时间',
            align:"center",
            dataIndex: 'establiTime'
          },
          {
            title:'企业网址',
            align:"center",
            dataIndex: 'entWebsite'
          },
          {
            title:'省份id',
            align:"center",
            dataIndex: 'province'
          },
          {
            title:'市id',
            align:"center",
            dataIndex: 'city'
          },
          {
            title:'区id',
            align:"center",
            dataIndex: 'area'
          },
          {
            title:'地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'主体介绍',
            align:"center",
            dataIndex: 'mainIntro'
          },
          {
            title:'经度',
            align:"center",
            dataIndex: 'gpsLongitude'
          },
          {
            title:'纬度',
            align:"center",
            dataIndex: 'gpsLatitude'
          },
          {
            title:'删除标志',
            align:"center",
            dataIndex: 'deleted'
          },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'auditStatus'
          },
          {
            title:'工作流id',
            align:"center",
            dataIndex: 'flowId'
          },
          {
            title:'企业名称',
            align:"center",
            dataIndex: 'entName'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/entinfo/wptpEntInfo/list",
          delete: "/entinfo/wptpEntInfo/delete",
          deleteBatch: "/entinfo/wptpEntInfo/deleteBatch",
          exportXlsUrl: "/entinfo/wptpEntInfo/exportXls",
          importExcelUrl: "entinfo/wptpEntInfo/importExcel",
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