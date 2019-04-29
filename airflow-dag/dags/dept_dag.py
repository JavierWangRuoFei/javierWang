#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
@program javierWang
@author: zhicao
@contact: zhicao@yscredit.com
@time: 2018-08-30 16:03
@version: 1.0.0
@description:

'''

import airflow
from airflow import DAG
from airflow.operators.bash_operator import BaseOperator
from airflow.operators.python_operator import PythonOperator
from datetime import timedelta

dept_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': airflow.utils.dates.days_ago(2),
    'email': ['bdwrfcug@163.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
    # 'queue': 'bash_queue',
    # 'pool': 'backfill',
    # 'priority_weight': 10,
    # 'end_date': datetime(2016, 1, 1),
    # 'wait_for_downstream': False,
    # 'dag': dag,
    # 'adhoc':False,
    # 'sla': timedelta(hours=2),
    # 'execution_timeout': timedelta(seconds=300),
    # 'on_failure_callback': some_function,
    # 'on_success_callback': some_other_function,
    # 'on_retry_callback': another_function,
    # 'trigger_rule': u'all_success'
}

#dag
dag = DAG(
    'dept_dag',
    default_args=dept_args,
    description='dept_dag',
    schedule_interval=timedelta(days=1)
)