import understand
db=understand.open('../testdb/test_sonic.udb')
methods  = db.ents("method")

thread_errors = []
runnable_errors = []

def error_msg(ref):
    return (ref.line(),ref.ent().simplename(),ref.file().name())

def populate_errors():
    for method in methods:
        if(method.longname().endswith('run')):
            ref = method.ref("DefineIn")
            if(ref != None):
                classes = ref.ent()
                parent_class_list = [ref.ent().name().lower() for ref in classes.refs("Couple")]
                errors = [error_msg(ref) for ref in method.refs('call')]
                if("thread" in parent_class_list):
                    thread_error += errors

                if("runnable"  in parent_class_list and "executor" not in parent_class_list):
                    runnable_error += errors


populate_errors()

for e in thread_errors:
    print('Did you intend to use Thread.start() to create a new execution thread?', e.line())

for e in runnable_errors:
    print('Did you intend to put run() method in new thread?', e.line())