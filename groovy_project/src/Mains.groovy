


// 读取 DSL 脚本内容
String MainDSL = new File("Main.gdsl").text

// 读取 Main.groovy 内容
String MainGroovy = new File("Main.groovy").text



// 组合脚本并执行
def script = """
${MainGroovy}
acceptConfigs {
    ${MainDSL}
}
"""


new GroovyShell().evaluate(script)
