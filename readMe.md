# spring batch note

JobConfiguration
JobDemo
FlowDemo
SplitDemo
DeciderDemo
ChildJob1、ChildJob2、ParentJob

Job
JobInstance: 一个job对应一个instance
JobExecution: job每次执行对应一个execution  （如果失败了，下一次执行...）
JobParameter: 给任务传参数，同一个job传不同参数就会生成不同instance
ExecutionContext: 执行上下文，存储数据、共享数据。step和job都有context

Flow
Flow: 是多个step的集合，可被多个job复用



