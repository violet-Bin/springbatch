# spring batch note

JobConfiguration 
JobDemo
FlowDemo
SplitDemo
MultiThreadStepDemoJob
DeciderDemo
ChildJob1、ChildJob2、ParentJob
ListenerDemo、ParametersDemo 监听传参
读取数据
ItemReaderDemo
ItemReaderDB
ItemReaderFile
ReStartItemReaderJob

写到库
ItemWriterToDBJob、processor
RetryDemoJob
SkipDemoJob

作业调度
JobLauncherDemo
JobOperatorDemo


Job：作业，由多个Step组成，封装整个批处理操作。
JobInstance：作业实例。每个作业执行时都会生成一个实例，实例被存放在JobRepository中。
JobExecution: job每次执行对应一个execution  （如果失败了，下一次执行...）
JobRepository：作业仓库，负责Job、Step执行过程中的状态保存。
JobParameter: 给任务传参数，同一个job传不同参数就会生成不同instance

Step：作业步，Job的一个执行环节，由多个或者一个Step组装成Job。
Tasklet：Step中具体执行逻辑的操作，可以重复执行，可以设置具体的同步、异步操作等。
Chunk：给定数量Item的集合，可以定义对Chunk的读操作、处理操作、写操作，提交间隔等。
ItemReader：从数据源（文件系统、数据库、队列等）中读取Item。
ItemProcessor：在Item写入数据源之前，对数据进行处理（如：数据清洗，数据转换，数据过滤，数据校验等）。
ItemWriter：将Item批量写入数据源（文件系统、数据库、队列等）。

Flow: 是多个step的集合，可被多个job复用

ExecutionContext: 执行上下文，存储数据、共享数据。step和job都有context

Listener：监听器。用来监听批处理作业的执行情况

作业调度器
JobLauncher
JobOperator




