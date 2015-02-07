use test;
drop table if exists classmate;
drop table if exists location;
drop table if exists tag;
create table location (
	id int(2) auto_increment primary key,
	name varchar(20) not null unique
);
create table classmate (
	id int(2) auto_increment primary key,
	username varchar(20) not null unique,	
	password char(32) not null,
	name nvarchar(4) not null,
	gender char(1) not null,
	birthday date,
	constellation nvarchar(3),
	mobile varchar(16),
	email	varchar(64),
	organization varchar(64),
	remark	varchar(64),
	lost tinyint default 0,
	location_id int(2) references location
);
create table tag (
	tag nvarchar(16) not null,
	username varchar(20) references classmate,
	primary key(tag, username)
);

insert into location(name) values('区内');
insert into location(name) values('北京');
insert into location(name) values('上海');
insert into location(name) values('深圳');
insert into location(name) values('西安');
insert into location(name) values('兰州');
insert into location(name) values('大连');
insert into location(name) values('天津');
insert into location(name) values('成都');
insert into location(name) values('浙江');
insert into location(name) values('安徽');
insert into location(name) values('澳洲');


delete from classmate;
insert into classmate(username, password, name, gender, birthday, mobile, email, organization, lost, location_id)
	values 
	('wangsheng', MD5('123456'), '王昇', 'M', '1976-1-19', '13995088188','wangsheng8999@gmail.com','宁夏医科大学总医院肿瘤医院外二科',0,1),
	('liubingshu', MD5('123456'), '刘炳枢', 'M', '1976-10-3', '13909558098','517128816@qq.com','吴忠市发改委',0,1),
	('maxiaoming', MD5('123456'), '马晓明', 'M', '1976-2-17', '15595248880','mxm197502@163.com','吴忠市环保监察支队',0,1),
	('liulihua', MD5('123456'), '刘丽华', 'F', '1977-2-15', '13995059933',NULL,'恒利批发超市',0,1),
	('majun', MD5('123456'), '马军', 'M', '1976-2-1', '18995063870','mj1849@foxmail.com','宁夏自治区人民医院骨科',0,1),
	('majianguo', MD5('123456'), '马建国', 'M', '1974-1-28', '18195393666','1126412472@qq.com','宁夏盐池县冯记沟',0,1),
	('yangshuchun', MD5('123456'), '杨树春', 'M', '1975-4-5', '13909553289','nxysc1975@163.com','宁夏青龙管业股份有限公司',0,1),
	('renlei', MD5('123456'), '任磊', 'M', '1975-1-8', '13519583152','renlei200018@163.com','宁夏自治区人民医院总院骨2科',0,1),
	('yangyong', MD5('123456'), '杨勇', 'M', '1975-2-17', '13895438099',NULL,'吴忠市委统战部',0,1),
	('mazhijun', MD5('123456'), '马志军', 'M', '1976-11-9', '18995336622','xjrymzj@163.com','夏进乳业',0,1),
	('yangzijian', MD5('123456'), '杨自健', 'M', '1975-8-20', '13895226292','656223789@qq.com','宁夏吴忠市利通区水务局',0,1),
	('majinping', MD5('123456'), '马金萍', 'F', NULL, '13629528833','yhdlzh1206@126.com','吴忠市利通区回民中学',0,1),
	('guochangfeng', MD5('123456'), '郭长峰', 'M', '1973-4-19', '13995092444','gcff2444@163.com','宁夏颐坤广告信息文化传媒有限公司',0,1),
	('tangyong', MD5('123456'), '唐勇', 'M', '1976-4-25', '13995139162','ty4025@163.com','宁夏艾克孚流体控制技术有限公司',0,1),
	('zhouguijun', MD5('123456'), '周贵军', 'M', '1975-3-28', '13895358128','1790694889@qq.com',NULL,0,1),
	('zhangbeiya', MD5('123456'), '张贝娅', 'F', '1976-3-28', '13519296696',NULL,'宁夏建设厅',0,1),
	('jinlei', MD5('123456'), '金磊', 'M', '1976-5-2', '18409605078','18409605078@163.com','银川电务段调度科',0,1),
	('yaohongmei', MD5('123456'), '姚红梅', 'F', '1976-4-18', '13709572257','420720667@qq.com','银川市公安局刑侦局技术支队',0,1),
	('cuimiao', MD5('123456'), '崔淼', 'F', '1976-10-26', '18709580762','sharry_cm@126.com','宁夏大学教育学院',0,1),
	('fengzhenhu', MD5('123456'), '冯振虎', 'M', '1974-6-25', '13835136478','610211586@qq.com','吴忠第二中学',0,1),
	('fanjun', MD5('123456'), '范军', 'M', '1974-8-22', '13309559210',NULL,'宁夏富荣农资销售连锁有限公司',0,1),
	('maligang', MD5('123456'), '马黎刚', 'M', '1975-12-16', '13995012396','mlghlj@163.com','宁夏银川市西夏区党委统战部',0,1),
	('yueliliang', MD5('123456'), '岳立亮', 'M', '1977-1-1', '13995172577','124463807@qq.com','宁夏机场酒店管理公司',0,1),
	('xiaoaiping', MD5('123456'), '肖爱平', 'M', '1975-5-7', '13014222645',NULL,'宁夏吴忠市广场服务中心',0,1),
	('lihongying', MD5('123456'), '李红英', 'F', '1976-12-23', '13723317934','87231036@qq.com','宁夏长庆油田',0,1),
	('yanghui', MD5('123456'), '杨辉', 'F', '1974-12-15', '13895009280',NULL,'宁夏银川市供电局',0,1),
	('maxueyan', MD5('123456'), '马学燕', 'F', NULL, NULL,NULL,NULL,1,1),
	('lijingxia', MD5('123456'), '李晋夏', 'F', NULL, NULL,NULL,NULL,1,1),
	('panzhibin', MD5('123456'), '潘志斌', 'M', NULL, NULL,NULL,NULL,1,1),
	('tianliguo', MD5('123456'), '田立国', 'M', NULL, NULL,NULL,NULL,1,1),
	('xiaoaihua', MD5('123456'), '肖爱华', 'F', NULL, NULL,NULL,NULL,1,1),
	('mabaofu', MD5('123456'), '马保福', 'M', NULL, NULL,NULL,NULL,1,1),
	('guizhilong', MD5('123456'), '桂志龙', 'M', NULL, NULL,NULL,NULL,1,1),
	('donghongzhong', MD5('123456'), '董红忠', 'M', NULL, NULL,NULL,NULL,1,1),
	('liubing', MD5('123456'), '刘兵', 'M', NULL, NULL,NULL,NULL,1,1),
	('maxueping', MD5('123456'), '马学平', 'M', NULL, NULL,NULL,NULL,1,1),	
	('manyihang', MD5('123456'), '满一航', 'M', '1976-12-7', '15810464404','manyhf16@163.com','北京百知尚行科技有限公司',0,2),
	('liuyang', MD5('123456'), '刘杨', 'F', '1976-3-24', '13621201008','helen_peipei@hotmail.com','北京诺金饭店',0,2),
	('bairunian', MD5('123456'), '白如念', 'M', '1976-9-5', '15117959197','brn2001@sina.com','北京市动物疫病预防控制中心',0,2),
	('mazhiling', MD5('123456'), '马志玲', 'F', '1976-11-28', '18601101077','987735054@qq.com','中国联通',0,2),
	('lining', MD5('123456'), '李宁', 'F', '1976-5-25', '13911596439','doctorlining@163.com','北京辉凌医药研究有限公司',0,2),
	('zhaowenlan', MD5('123456'), '赵文澜', 'F', '1976-8-14', '13146656099','737879412@qq.com',NULL,0,2),
	('sheruifang', MD5('123456'), '佘瑞芳', 'F', '1976-4-28', '13601374857','1356103747@qq.com','北京市海淀区花园路社区卫生服务中心',0,2),
	('jinzhihua', MD5('123456'), '金志华', 'M', '1977-3-18', '18603038282','jinzhihua2004@hotmail.com','金宇浩兴农牧业有限公司',0,3),
	('macheng', MD5('123456'), '马诚', 'M', '1977-5-14', '13918251326','bingomac@163.com','通用电气',0,3),
	('bipengyu', MD5('123456'), '毕鹏宇', 'F', '1976-4-4', '18917706120','pengyu.bi@163.com','上海中华职业教育社',0,3),
	('luzhiyun', MD5('123456'), '鲁志云', 'M', '1976-6-10', '13611894676','luzhiyun@gmail.com','PGW匹兹堡玻璃',0,3),
	('shenjie', MD5('123456'), '沈洁', 'F', NULL, NULL,NULL,NULL,1,3),
	('liangkai', MD5('123456'), '梁凯', 'M', NULL, NULL,NULL,NULL,1,3),
	('yandoudou', MD5('123456'), '闫豆豆', 'F', '1976-10-9', '13632673857','ydd109@126.com','深圳市爱溪尔科技有限公司',0,4),
	('jianghaitao', MD5('123456'), '江海涛', 'M', '1976-6-14', '13828892967','149104196@qq.com','深圳飞博远创技术有限公司',0,4),
	('yanghong', MD5('123456'), '杨虹', 'F', '1976-5-9', '13759966655','863561219@qq.com','西安市莲湖区41中',0,5),
	('hanhaidong', MD5('123456'), '韩海东', 'M', '1977-7-5', '13893150820','leopard1048@163.com','中科院寒区早区环境与工程研究所',0,6),
	('lixiaolong', MD5('123456'), '李小龙', 'M', '1976-5-15', '13050504808','dldtlxl@sina.com','大连大峰野金属有限公司',0,7),
	('xieli', MD5('123456'), '谢荔', 'F', '1975-7-25', '13662031753','zshelldeepsea@163.com','天津市和平区第二十中学',0,8),
	('zhuyujuan', MD5('123456'), '朱玉娟', 'F', '1975-6-11', '18628183531','irenezyj@126.com','四川省成都市腾讯公司',0,9),
	('chenhuan', MD5('123456'), '陈欢', 'F', '1977-2-15', '13566780241','11539123@qq.com','杭州金耐贸易有限公司',0,10),
	('liujinghua', MD5('123456'), '刘靖华', 'M', '1976-5-7', '13956399509','ljh_751@126.com','中铁四局集团电气化公司',0,11),
	('lidongmei', MD5('123456'), '李冬梅', 'F', '1976-12-24', '0061405533909','lidongmei1224@hotmail.com','MQI Inernational Pty Ltd',0,12);

delete from tag;
insert into tag values('班长','wangsheng');
insert into tag values('数学课代表','sheruifang');
insert into tag values('物理课代表','zhaowenlan');
insert into tag values('英语课代表','bipengyu');
insert into tag values('语文课代表','shenjie');
insert into tag values('体育委员','lixiaolong');
insert into tag values('体育委员','maxiaoming');
insert into tag values('大龙','lixiaolong');
insert into tag values('驴','jinzhihua');
insert into tag values('虎子','fengzhenhu');
insert into tag values('皇上','mabaofu');
insert into tag values('潘冬子','hanhaidong');
insert into tag values('沫子王','zhouguijun');
insert into tag values('增增','manyihang');
insert into tag values('学霸','jinzhihua');
insert into tag values('学霸','shenjie');
insert into tag values('学霸','mazhiling');
insert into tag values('学霸','lineng');
insert into tag values('学霸','jianghaitao');
insert into tag values('学霸','bipengyu');
insert into tag values('学霸','mabaofu');
insert into tag values('学霸','macheng');
insert into tag values('球星','jinzhihua');
insert into tag values('球星','luzhiyun');
insert into tag values('球星','tangyong');
insert into tag values('球星','yangshuchun');
insert into tag values('球星','banzhibin');
insert into tag values('球星','zhouguijun');
insert into tag values('球星','donghongzhong');
insert into tag values('球星','mazhijun');

drop table if exists constellation;
create table constellation(
	begin date,
	end date,
	name	nvarchar(3)
);
insert into constellation values 
('1999-12-22','2000-1-19','魔羯座'),
('2000-1-20','2000-2-18','水瓶座'),
('2000-2-19','2000-3-20','双鱼座'),
('2000-3-21','2000-4-19','牡羊座'),
('2000-4-20','2000-5-20','金牛座'),
('2000-5-21','2000-6-21','双子座'),
('2000-6-22','2000-7-22','巨蟹座'),
('2000-7-23','2000-8-22','狮子座'),
('2000-8-23','2000-9-22','处女座'),
('2000-9-23','2000-10-23','天秤座'),
('2000-10-24','2000-11-22','天蝎座'),
('2000-11-23','2000-12-21','射手座'),
('2000-12-22','2001-1-19','魔羯座');

select id,a.name,birthday, begin,str_to_date(CONCAT('2000-',month(birthday),'-',day(birthday)),'%Y-%m-%d'),end, b.name,month(birthday)-month(begin) from classmate a, constellation b 
	where str_to_date(CONCAT('2000-',month(birthday),'-',day(birthday)),'%Y-%m-%d') between begin and end 
		and (month(birthday)-month(begin)) in (0,1,-11) order by month(birthday), day(birthday);
		
update classmate a set constellation =  (select b.name from constellation b 
	where str_to_date(CONCAT('2000-',month(a.birthday),'-',day(a.birthday)),'%Y-%m-%d') between b.begin and b.end 
		and (month(a.birthday)-month(b.begin)) in (0,1,-11));
