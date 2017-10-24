/* 公众号管理模块 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_PUBLIC` (
  `PUBLIC_CODE` VARCHAR(255) NOT NULL COMMENT '公众号 code',
  `OPEN_ID` VARCHAR(255) NULL COMMENT '公众号 openid',
  `PUBLIC_NAME` VARCHAR(255) NOT NULL COMMENT '公众号名称',
  `PUBLIC_NICKNAME` VARCHAR(255) NOT NULL COMMENT '公众微信号',
  `APP_ID` VARCHAR(255) NOT NULL COMMENT '应用 ID',
  `APP_SERCT` VARCHAR(255) NOT NULL COMMENT '应用秘钥',
  `TOKEN` VARCHAR(255) NOT NULL COMMENT '令牌',
  `AESKEY` VARCHAR(255) NOT NULL COMMENT '消息加密密钥',
  `URL` VARCHAR(255) NOT NULL COMMENT '服务器地址',
  `CREATOR` VARCHAR(255) NULL COMMENT '创建者',
  `CREATE_DATE` VARCHAR(50) NULL COMMENT '创建时间',
  `MODIFY_DATE` VARCHAR(50) NULL COMMENT '修改时间',
  `MODIFYER` VARCHAR(255) NULL COMMENT '修改人',
  `DEL_FLAG` INT(11) NULL COMMENT '是否删除。0为已删除，1为没删除。',
  `SYSTEM_CODE` VARCHAR(255) NULL COMMENT '系统标识\n',
  `IMG_CODE` VARCHAR(255) NULL,
  PRIMARY KEY (`PUBLIC_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '公众号管理模块'

/* 公众号管理--头像、二维码 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_PUB_IMG` (
  `IMG_CODE` VARCHAR(255) NOT NULL COMMENT '主键',
  `HEADIMG` BLOB NOT NULL COMMENT '微信公众号的头像文件',
  `QRCODE` BLOB NOT NULL COMMENT '微信公众号的二维码图片文件',
  PRIMARY KEY (`IMG_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '自动回复模块--微信关键字表'

/* 自动回复模块，有关注时回复、非关键词回复、关键词回复三个主要功能。 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_REPLY` (
  `REPLY_CODE` VARCHAR(255) NOT NULL COMMENT '主键',
  `PUBLIC_CODE` VARCHAR(255) NOT NULL COMMENT '公众号主键',
  `REPLY_TYPE` INT(11) NOT NULL COMMENT '消息回复类型。0为关注时回复，1为非关键词消息默认回复，2为关键词回复。',
  `CONTENT` VARCHAR(255) NULL COMMENT '回复的内容',
  `REPLY_FLAG` INT(11) NOT NULL DEFAULT 1 COMMENT '回复是否开启。0为关闭，1为开启，默认为1开启',
  PRIMARY KEY (`REPLY_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '自动回复模块，有关注时回复、非关键词回复' /* comment truncated */ /*、关键词回复三个主要功能。*/

/* 自动回复模块--规则表 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_REP_RULE` (
  `RULE_CODE` VARCHAR(255) NOT NULL COMMENT '主键',
  `PUBLIC_CODE` VARCHAR(255) NOT NULL COMMENT '公众号主键\n',
  `RULE_NAME` VARCHAR(255) NOT NULL COMMENT '规则名称',
  `USE_FLAG` INT(11) NOT NULL COMMENT '关键词回复中规则是否开启。0为关闭，1为开启。',
  `KEFU_REPLY_FLAG` INT(11) NOT NULL COMMENT '客服回复',
  `CONTENT` VARCHAR(255) NOT NULL COMMENT '回复内容',
  `CREATOR` VARCHAR(255) NULL COMMENT '创建人',
  `CREATE_DATE` VARCHAR(50) NULL COMMENT '创建时间',
  `MODIFY_DATE` VARCHAR(50) NULL COMMENT '修改时间',
  `MODIFYER` VARCHAR(255) NULL COMMENT '修改人',
  `DEL_FLAG` INT(11) NULL COMMENT '是否删除。0为已删除，1为没删除。',
  PRIMARY KEY (`RULE_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '自动回复模块--规则表'

/* 自动回复模块--微信关键字表 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_REP_KEYWORD` (
  `KEYWORD_CODE` VARCHAR(255) NOT NULL COMMENT '规则主键',
  `RULE_CODE` VARCHAR(255) NOT NULL COMMENT '主键',
  `KEYWORD` VARCHAR(255) NOT NULL COMMENT '关键字',
  `MATCH_FLAG` INT(11) NOT NULL COMMENT '匹配规则，1为完全匹配，0为模糊匹配',
  `DEL_FLAG` INT(11) NULL COMMENT '是否删除。0为已删除，1为没删除。',
  PRIMARY KEY (`KEYWORD_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '自动回复模块--微信关键字表'

/* 消息管理模块--定点推送 */
CREATE TABLE IF NOT EXISTS `mdweixin`.`MP_YY_MSG_PUSH` (
  `PUSH_CODE` VARCHAR(255) NOT NULL COMMENT '推送消息 code',
  `PUBLIC_CODE` VARCHAR(255) NOT NULL COMMENT '公众号主键code',
  `PUSH_NAME` VARCHAR(255) NOT NULL COMMENT '推送名称',
  `LATITUDE` VARCHAR(255) NOT NULL COMMENT '纬度',
  `LONGITUDE` VARCHAR(255) NOT NULL COMMENT '经度',
  `RADIUS` VARCHAR(255) NOT NULL COMMENT '推送半径',
  `CONTENT` VARCHAR(255) NOT NULL COMMENT '推送内容',
  `PUSH_FLAG` INT(11) NOT NULL COMMENT '是否开启该推送。0为关闭，1位开启。',
  `CREATOR` VARCHAR(255) NULL COMMENT '创建者',
  `CREATE_DATE` VARCHAR(50) NULL COMMENT '创建时间',
  `MODIFY_DATE` VARCHAR(50) NULL COMMENT '修改时间',
  `MODIFYER` VARCHAR(255) NULL COMMENT '修改者',
  `DEL_FLAG` INT(11) NULL,
  PRIMARY KEY (`PUSH_CODE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '消息管理模块--定点推送'


