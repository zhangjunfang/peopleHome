TODO LIST:

1、目前所用的为mybatis内存分页,需要进化为物理分页
2、需要开发相关的物化视图的工作
3、充值的奖励体现不出来用触发用户,需要加上(您邀请的好友已达到邀请奖励的条件，系统补发邀请奖励5.00元)(这个列表要每天定时生成)
4、SELECT USER_ID, SUM(MONEY) MONEY
     FROM (SELECT REPLACE(REPLACE(REGEXP_SUBSTR(REMARK, '邀请\w+成为本站'),
                                  '邀请',
                                  ''),
                          '成为本站',
                          '') USER_ID,
                  MONEY
             FROM USERS_ACCOUNT_LOG T
            WHERE 1 = 1
              AND T.LOG_TYPE IN ('02', '55', '56', '70', '59', '38')
              AND USER_ID = 'lvlf')
    WHERE USER_ID IS NOT NULL
    GROUP BY USER_ID
    
5、好友邀请奖励需要考虑性能问题
