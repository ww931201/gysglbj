/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.4.0

Source Server         : gysglbj
Source Server Version : 90200
Source Host           : 10.106.2.223:1521
Source Schema         : GYSGLBJ

Target Server Type    : ORACLE
Target Server Version : 90200
File Encoding         : 65001

Date: 2017-06-30 10:30:20
*/


-- ----------------------------
-- Table structure for DIC_POPEDOM
-- ----------------------------
DROP TABLE "GYSGLBJ"."DIC_POPEDOM";
CREATE TABLE "GYSGLBJ"."DIC_POPEDOM" (
"ID" VARCHAR2(50 BYTE) DEFAULT sys_guid()  NOT NULL ,
"POPEDOM_NAME" VARCHAR2(50 BYTE) NULL ,
"FORWARD_ACTION" VARCHAR2(100 BYTE) NULL ,
"POPEDOM_TYPE" VARCHAR2(50 BYTE) NULL ,
"XH" NUMBER(4) NULL ,
"REMARK" VARCHAR2(50 BYTE) NULL ,
"FIELD1" NUMBER NULL ,
"FIELD2" VARCHAR2(200 BYTE) NULL ,
"PARENT_ID" VARCHAR2(50 BYTE) DEFAULT 0  NULL ,
"TARGET" VARCHAR2(50 BYTE) DEFAULT null  NULL ,
"NAV_ICON" VARCHAR2(300 BYTE) DEFAULT null  NULL ,
"NAV_ICON_WIDTH" VARCHAR2(3 BYTE) DEFAULT null  NULL ,
"NAV_ICON_HEIGHT" VARCHAR2(3 BYTE) DEFAULT null  NULL ,
"IS_MENU" NUMBER(1) DEFAULT 2  NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "GYSGLBJ"."DIC_POPEDOM" IS '权限表';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."ID" IS '主键';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."POPEDOM_NAME" IS '名称';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."FORWARD_ACTION" IS 'URL';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."POPEDOM_TYPE" IS '权限类型,关联DIC_POPEDOM_TYPE';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."XH" IS '序号';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."REMARK" IS '备注';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."FIELD1" IS '预留字段1';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."FIELD2" IS '预留字段2';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."PARENT_ID" IS '父级ID';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."TARGET" IS '目标iframe容器id';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."NAV_ICON" IS '图标路径';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."NAV_ICON_WIDTH" IS '图标显示宽度';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."NAV_ICON_HEIGHT" IS '图标显示高度';
COMMENT ON COLUMN "GYSGLBJ"."DIC_POPEDOM"."IS_MENU" IS '是否是菜单 1 是 2 否';

-- ----------------------------
-- Records of DIC_POPEDOM
-- ----------------------------
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4E85D7240102B0C0E0537F000001B0C0', '系统管理', null, null, '299', null, null, null, '0', null, null, null, null, '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3A7FB1C2E3A080E0537F000001A080', '主要权限配置', null, null, '23', null, null, null, '0', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3A7FB1C2E4A080E0537F000001A080', '主页', '/', null, '24', null, null, null, '4F3A7FB1C2E3A080E0537F000001A080', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3A7FB1C2E5A080E0537F000001A080', '左侧菜单页面', '/left', null, '25', null, null, null, '4F3A7FB1C2E3A080E0537F000001A080', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3A7FB1C2E6A080E0537F000001A080', '右侧内容页面', '/open', null, '26', null, null, null, '4F3A7FB1C2E3A080E0537F000001A080', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3C76D2C3E3F002E0537F000001F002', '角色列表', '/role/list', null, '28', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F138DC1A791B040E0537F000001B040', '用户管理', '/user', null, '6', null, null, null, '4E85D7240102B0C0E0537F000001B0C0', 'frmright', '/libs/icons/png/14.png', '40', '40', '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F138DC1A792B040E0537F000001B040', '角色管理', '/role', null, '5', null, null, null, '4E85D7240102B0C0E0537F000001B0C0', 'frmright', '/libs/icons/png/15.png', '40', '40', '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F138DC1A793B040E0537F000001B040', '权限管理', '/popedom', null, '7', null, null, null, '4E85D7240102B0C0E0537F000001B0C0', 'frmright', '/libs/icons/png/13.png', '40', '40', '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3C14B4908B708EE0537F000001708E', '权限列表', '/popedomMatch/list', null, '43', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F34104EE0537F000001104E', '注销', '/login/logOut', null, '32', null, null, null, '4F3A7FB1C2E3A080E0537F000001A080', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F38104EE0537F000001104E', '修改', '/popedom/update', null, '36', null, null, null, '4F138DC1A793B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F36104EE0537F000001104E', '权限管理列表', '/popedom/list', null, '34', null, null, null, '4F138DC1A793B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F37104EE0537F000001104E', '保存', '/popedom/save', null, '35', null, null, null, '4F138DC1A793B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F39104EE0537F000001104E', '删除', '/popedom/delete', null, '37', null, null, null, '4F138DC1A793B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3A104EE0537F000001104E', '移动顺序', '/popedom/exchangePosition', null, '38', null, null, null, '4F138DC1A793B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3B104EE0537F000001104E', '保存权限', '/popedomMatch/save', null, '44', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3C104EE0537F000001104E', '添加页面', '/role/addView', null, '29', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3D104EE0537F000001104E', '修改页面', '/role/updateView', null, '40', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3E104EE0537F000001104E', '详情页面', '/role/detailView', null, '39', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F3F104EE0537F000001104E', '删除', '/role/delete', null, '42', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F40104EE0537F000001104E', '添加页面', '/user/addView', null, '49', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F41104EE0537F000001104E', '单位列表', '/user/unitList', null, '55', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F42104EE0537F000001104E', '科室列表', '/user/officeList', null, '56', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E74648F43104EE0537F000001104E', '角色列表', '/user/roleList', null, '57', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2CB00B0E0537F00000100B0', '保存/修改角色', '/role/save', null, '41', null, null, null, '4F138DC1A792B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2D000B0E0537F00000100B0', '修改页面', '/user/updateView', null, '50', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2D100B0E0537F00000100B0', '详情页面', '/user/detailView', null, '51', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2D200B0E0537F00000100B0', '保存/修改', '/user/save', null, '52', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2D300B0E0537F00000100B0', '删除', '/user/delete', null, '53', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E75BAE2D400B0E0537F00000100B0', '批量删除', '/user/deleteBatch', null, '54', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('51E1C295C2BDE042E0537F000001E042', '保存密码', '/user/savePasswd', null, '278', null, null, null, '4F3A7FB1C2E3A080E0537F000001A080', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('4F3E2AF25FC620A4E0537F00000120A4', '用户列表', '/user/list', null, '30', null, null, null, '4F138DC1A791B040E0537F000001B040', null, null, null, null, '2');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('5288C902BCFDB0CEE0537F000001B0CE', '供应商管理', '/gys', null, '300', null, null, null, '5288C587739E9060E0537F0000019060', 'frmright', '/libs/icons/png/18.png', '40', '40', '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('5324CB0E9DDEC0D2E0537F000001C0D2', '管理', null, null, '313', null, null, null, '0', null, null, null, null, '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('52D509479C1D003EE0537F000001003E', '项目', '/project', null, '309', null, null, null, '52D4D4EBB25F2014E0537F0000012014', 'frmright', '/libs/icons/png/66.png', '40', '40', '1');
INSERT INTO "GYSGLBJ"."DIC_POPEDOM" VALUES ('5324CB0E9DDFC0D2E0537F000001C0D2', '管理', '/gys', null, '314', null, null, null, '5324CB0E9DDEC0D2E0537F000001C0D2', null, null, null, null, '2');

-- ----------------------------
-- Indexes structure for table DIC_POPEDOM
-- ----------------------------

-- ----------------------------
-- Triggers structure for table DIC_POPEDOM
-- ----------------------------
CREATE OR REPLACE TRIGGER "GYSGLBJ"."DIC_POPEDOM_XH_TRIGGER" BEFORE INSERT ON "GYSGLBJ"."DIC_POPEDOM" REFERENCING OLD AS "OLD" NEW AS "NEW" FOR EACH ROW WHEN (new.XH is null)
begin
  select DIC_POPEDOM_XH.nextval into :new.XH from sys.dual;
end dic_popedom_xh_trigger;

-- ----------------------------
-- Checks structure for table DIC_POPEDOM
-- ----------------------------
ALTER TABLE "GYSGLBJ"."DIC_POPEDOM" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "GYSGLBJ"."DIC_POPEDOM" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table DIC_POPEDOM
-- ----------------------------
ALTER TABLE "GYSGLBJ"."DIC_POPEDOM" ADD PRIMARY KEY ("ID");
