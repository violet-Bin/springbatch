# spring batch note

JobConfiguration \ ListenerDemo -> chunk创建 
JobDemo
FlowDemo
SplitDemo
DeciderDemo
ChildJob1、ChildJob2、ParentJob
ListenerDemo、ParametersDemo 监听传参
读取数据
ItemReaderDemo
ItemReaderDB
ItemReaderFile
ReStartItemReaderJob
ItemWriterDBJob

写到库
ItemWriterToDBJob、processor
RetryDemoJob
SkipDemoJob


Job
JobInstance: 一个job对应一个instance
JobExecution: job每次执行对应一个execution  （如果失败了，下一次执行...）
JobParameter: 给任务传参数，同一个job传不同参数就会生成不同instance
ExecutionContext: 执行上下文，存储数据、共享数据。step和job都有context

Flow
Flow: 是多个step的集合，可被多个job复用

监听器
用来监听批处理作业的执行情况




