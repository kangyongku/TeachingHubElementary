/*************************************************************
 *
 *  MathJax.js
 *  
 *  The main code for the MathJax math-typesetting library.  See 
 *  http://www.mathjax.org/ for details.
 *  
 *  ---------------------------------------------------------------------
 *  
 *  Copyright (c) 2009-2013 The MathJax Consortium
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
if (!window.MathJax) {
    window.MathJax = {}
}

MathJax.isPacked = true;

if (document.getElementById && document.childNodes && document.createElement) {
    if (!window.MathJax) {
        window.MathJax = {}
    }
    if (!MathJax.Hub) {
        MathJax.version = "2.2";
        MathJax.fileversion = "2.2";
        (function(d) {
            var b = window[d];
            if (!b) {
                b = window[d] = {}
            }
            var f = [];
            var c = function(g) {
                var h = g.constructor;
                if (!h) {
                    h = new Function("")
                }
                for (var i in g) {
                    if (i !== "constructor" && g.hasOwnProperty(i)) {
                        h[i] = g[i]
                    }
                }
                return h
            };
            var a = function() {
                return new Function("return arguments.callee.Init.call(this,arguments)")
            };
            var e = a();
            e.prototype = {
                bug_test: 1
            };
            if (!e.prototype.bug_test) {
                a = function() {
                    return function() {
                        return arguments.callee.Init.call(this, arguments)
                    }
                }
            }
            b.Object = c({
                constructor: a(),
                Subclass: function(g, i) {
                    var h = a();
                    h.SUPER = this;
                    h.Init = this.Init;
                    h.Subclass = this.Subclass;
                    h.Augment = this.Augment;
                    h.protoFunction = this.protoFunction;
                    h.can = this.can;
                    h.has = this.has;
                    h.isa = this.isa;
                    h.prototype = new this(f);
                    h.prototype.constructor = h;
                    h.Augment(g, i);
                    return h
                },
                Init: function(g) {
                    var h = this;
                    if (g.length === 1 && g[0] === f) {
                        return h
                    }
                    if (!(h instanceof g.callee)) {
                        h = new g.callee(f)
                    }
                    return h.Init.apply(h, g) || h
                },
                Augment: function(g, h) {
                    var i;
                    if (g != null) {
                        for (i in g) {
                            if (g.hasOwnProperty(i)) {
                                this.protoFunction(i, g[i])
                            }
                        }
                        if (g.toString !== this.prototype.toString && g.toString !== {}.toString) {
                            this.protoFunction("toString", g.toString)
                        }
                    }
                    if (h != null) {
                        for (i in h) {
                            if (h.hasOwnProperty(i)) {
                                this[i] = h[i]
                            }
                        }
                    }
                    return this
                },
                protoFunction: function(h, g) {
                    this.prototype[h] = g;
                    if (typeof g === "function") {
                        g.SUPER = this.SUPER.prototype
                    }
                },
                prototype: {
                    Init: function() {},
                    SUPER: function(g) {
                        return g.callee.SUPER
                    },
                    can: function(g) {
                        return typeof(this[g]) === "function"
                    },
                    has: function(g) {
                        return typeof(this[g]) !== "undefined"
                    },
                    isa: function(g) {
                        return (g instanceof Object) && (this instanceof g)
                    }
                },
                can: function(g) {
                    return this.prototype.can.call(this, g)
                },
                has: function(g) {
                    return this.prototype.has.call(this, g)
                },
                isa: function(h) {
                    var g = this;
                    while (g) {
                        if (g === h) {
                            return true
                        } else {
                            g = g.SUPER
                        }
                    }
                    return false
                },
                SimpleSUPER: c({
                    constructor: function(g) {
                        return this.SimpleSUPER.define(g)
                    },
                    define: function(g) {
                        var i = {};
                        if (g != null) {
                            for (var h in g) {
                                if (g.hasOwnProperty(h)) {
                                    i[h] = this.wrap(h, g[h])
                                }
                            }
                            if (g.toString !== this.prototype.toString && g.toString !== {}.toString) {
                                i.toString = this.wrap("toString", g.toString)
                            }
                        }
                        return i
                    },
                    wrap: function(i, h) {
                        if (typeof(h) === "function" && h.toString().match(/\.\s*SUPER\s*\(/)) {
                            var g = new Function(this.wrapper);
                            g.label = i;
                            g.original = h;
                            h = g;
                            g.toString = this.stringify
                        }
                        return h
                    },
                    wrapper: function() {
                        var h = arguments.callee;
                        this.SUPER = h.SUPER[h.label];
                        try {
                            var g = h.original.apply(this, arguments)
                        } catch (i) {
                            delete this.SUPER;
                            throw i
                        }
                        delete this.SUPER;
                        return g
                    }.toString().replace(/^\s*function\s*\(\)\s*\{\s*/i, "").replace(/\s*\}\s*$/i, ""),
                    toString: function() {
                        return this.original.toString.apply(this.original, arguments)
                    }
                })
            })
        })("MathJax");
        (function(BASENAME) {
            var BASE = window[BASENAME];
            if (!BASE) {
                BASE = window[BASENAME] = {}
            }
            var CALLBACK = function(data) {
                var cb = new Function("return arguments.callee.execute.apply(arguments.callee,arguments)");
                for (var id in CALLBACK.prototype) {
                    if (CALLBACK.prototype.hasOwnProperty(id)) {
                        if (typeof(data[id]) !== "undefined") {
                            cb[id] = data[id]
                        } else {
                            cb[id] = CALLBACK.prototype[id]
                        }
                    }
                }
                cb.toString = CALLBACK.prototype.toString;
                return cb
            };
            CALLBACK.prototype = {
                isCallback: true,
                hook: function() {},
                data: [],
                object: window,
                execute: function() {
                    if (!this.called || this.autoReset) {
                        this.called = !this.autoReset;
                        return this.hook.apply(this.object, this.data.concat([].slice.call(arguments, 0)))
                    }
                },
                reset: function() {
                    delete this.called
                },
                toString: function() {
                    return this.hook.toString.apply(this.hook, arguments)
                }
            };
            var ISCALLBACK = function(f) {
                return (typeof(f) === "function" && f.isCallback)
            };
            var EVAL = function(code) {
                return eval.call(window, code)
            };
            EVAL("var __TeSt_VaR__ = 1");
            if (window.__TeSt_VaR__) {
                try {
                    delete window.__TeSt_VaR__
                } catch (error) {
                    window.__TeSt_VaR__ = null
                }
            } else {
                if (window.execScript) {
                    EVAL = function(code) {
                        BASE.__code = code;
                        code = "try {" + BASENAME + ".__result = eval(" + BASENAME + ".__code)} catch(err) {" + BASENAME + ".__result = err}";
                        window.execScript(code);
                        var result = BASE.__result;
                        delete BASE.__result;
                        delete BASE.__code;
                        if (result instanceof Error) {
                            throw result
                        }
                        return result
                    }
                } else {
                    EVAL = function(code) {
                        BASE.__code = code;
                        code = "try {" + BASENAME + ".__result = eval(" + BASENAME + ".__code)} catch(err) {" + BASENAME + ".__result = err}";
                        var head = (document.getElementsByTagName("head"))[0];
                        if (!head) {
                            head = document.body
                        }
                        var script = document.createElement("script");
                        script.appendChild(document.createTextNode(code));
                        head.appendChild(script);
                        head.removeChild(script);
                        var result = BASE.__result;
                        delete BASE.__result;
                        delete BASE.__code;
                        if (result instanceof Error) {
                            throw result
                        }
                        return result
                    }
                }
            }
            var USING = function(args, i) {
                if (arguments.length > 1) {
                    if (arguments.length === 2 && !(typeof arguments[0] === "function") && arguments[0] instanceof Object && typeof arguments[1] === "number") {
                        args = [].slice.call(args, i)
                    } else {
                        args = [].slice.call(arguments, 0)
                    }
                }
                if (args instanceof Array && args.length === 1) {
                    args = args[0]
                }
                if (typeof args === "function") {
                    if (args.execute === CALLBACK.prototype.execute) {
                        return args
                    }
                    return CALLBACK({
                        hook: args
                    })
                } else {
                    if (args instanceof Array) {
                        if (typeof(args[0]) === "string" && args[1] instanceof Object && typeof args[1][args[0]] === "function") {
                            return CALLBACK({
                                hook: args[1][args[0]],
                                object: args[1],
                                data: args.slice(2)
                            })
                        } else {
                            if (typeof args[0] === "function") {
                                return CALLBACK({
                                    hook: args[0],
                                    data: args.slice(1)
                                })
                            } else {
                                if (typeof args[1] === "function") {
                                    return CALLBACK({
                                        hook: args[1],
                                        object: args[0],
                                        data: args.slice(2)
                                    })
                                }
                            }
                        }
                    } else {
                        if (typeof(args) === "string") {
                            return CALLBACK({
                                hook: EVAL,
                                data: [args]
                            })
                        } else {
                            if (args instanceof Object) {
                                return CALLBACK(args)
                            } else {
                                if (typeof(args) === "undefined") {
                                    return CALLBACK({})
                                }
                            }
                        }
                    }
                }
                throw Error("Can't make callback from given data")
            };
            var DELAY = function(time, callback) {
                callback = USING(callback);
                callback.timeout = setTimeout(callback, time);
                return callback
            };
            var WAITFOR = function(callback, signal) {
                callback = USING(callback);
                if (!callback.called) {
                    WAITSIGNAL(callback, signal);
                    signal.pending++
                }
            };
            var WAITEXECUTE = function() {
                var signals = this.signal;
                delete this.signal;
                this.execute = this.oldExecute;
                delete this.oldExecute;
                var result = this.execute.apply(this, arguments);
                if (ISCALLBACK(result) && !result.called) {
                    WAITSIGNAL(result, signals)
                } else {
                    for (var i = 0, m = signals.length; i < m; i++) {
                        signals[i].pending--;
                        if (signals[i].pending <= 0) {
                            signals[i].call()
                        }
                    }
                }
            };
            var WAITSIGNAL = function(callback, signals) {
                if (!(signals instanceof Array)) {
                    signals = [signals]
                }
                if (!callback.signal) {
                    callback.oldExecute = callback.execute;
                    callback.execute = WAITEXECUTE;
                    callback.signal = signals
                } else {
                    if (signals.length === 1) {
                        callback.signal.push(signals[0])
                    } else {
                        callback.signal = callback.signal.concat(signals)
                    }
                }
            };
            var AFTER = function(callback) {
                callback = USING(callback);
                callback.pending = 0;
                for (var i = 1, m = arguments.length; i < m; i++) {
                    if (arguments[i]) {
                        WAITFOR(arguments[i], callback)
                    }
                }
                if (callback.pending === 0) {
                    var result = callback();
                    if (ISCALLBACK(result)) {
                        callback = result
                    }
                }
                return callback
            };
            var HOOKS = MathJax.Object.Subclass({
                Init: function(reset) {
                    this.hooks = [];
                    this.reset = reset
                },
                Add: function(hook, priority) {
                    if (priority == null) {
                        priority = 10
                    }
                    if (!ISCALLBACK(hook)) {
                        hook = USING(hook)
                    }
                    hook.priority = priority;
                    var i = this.hooks.length;
                    while (i > 0 && priority < this.hooks[i - 1].priority) {
                        i--
                    }
                    this.hooks.splice(i, 0, hook);
                    return hook
                },
                Remove: function(hook) {
                    for (var i = 0, m = this.hooks.length; i < m; i++) {
                        if (this.hooks[i] === hook) {
                            this.hooks.splice(i, 1);
                            return
                        }
                    }
                },
                Execute: function() {
                    var callbacks = [{}];
                    for (var i = 0, m = this.hooks.length; i < m; i++) {
                        if (this.reset) {
                            this.hooks[i].reset()
                        }
                        var result = this.hooks[i].apply(window, arguments);
                        if (ISCALLBACK(result) && !result.called) {
                            callbacks.push(result)
                        }
                    }
                    if (callbacks.length === 1) {
                        return null
                    }
                    if (callbacks.length === 2) {
                        return callbacks[1]
                    }
                    return AFTER.apply({}, callbacks)
                }
            });
            var EXECUTEHOOKS = function(hooks, data, reset) {
                if (!hooks) {
                    return null
                }
                if (!(hooks instanceof Array)) {
                    hooks = [hooks]
                }
                if (!(data instanceof Array)) {
                    data = (data == null ? [] : [data])
                }
                var handler = HOOKS(reset);
                for (var i = 0, m = hooks.length; i < m; i++) {
                    handler.Add(hooks[i])
                }
                return handler.Execute.apply(handler, data)
            };
            var QUEUE = BASE.Object.Subclass({
                Init: function() {
                    this.pending = 0;
                    this.running = 0;
                    this.queue = [];
                    this.Push.apply(this, arguments)
                },
                Push: function() {
                    var callback;
                    for (var i = 0, m = arguments.length; i < m; i++) {
                        callback = USING(arguments[i]);
                        if (callback === arguments[i] && !callback.called) {
                            callback = USING(["wait", this, callback])
                        }
                        this.queue.push(callback)
                    }
                    if (!this.running && !this.pending) {
                        this.Process()
                    }
                    return callback
                },
                Process: function(queue) {
                    while (!this.running && !this.pending && this.queue.length) {
                        var callback = this.queue[0];
                        queue = this.queue.slice(1);
                        this.queue = [];
                        this.Suspend();
                        var result = callback();
                        this.Resume();
                        if (queue.length) {
                            this.queue = queue.concat(this.queue)
                        }
                        if (ISCALLBACK(result) && !result.called) {
                            WAITFOR(result, this)
                        }
                    }
                },
                Suspend: function() {
                    this.running++
                },
                Resume: function() {
                    if (this.running) {
                        this.running--
                    }
                },
                call: function() {
                    this.Process.apply(this, arguments)
                },
                wait: function(callback) {
                    return callback
                }
            });
            var SIGNAL = QUEUE.Subclass({
                Init: function(name) {
                    QUEUE.prototype.Init.call(this);
                    this.name = name;
                    this.posted = [];
                    this.listeners = HOOKS(true)
                },
                Post: function(message, callback, forget) {
                    callback = USING(callback);
                    if (this.posting || this.pending) {
                        this.Push(["Post", this, message, callback, forget])
                    } else {
                        this.callback = callback;
                        callback.reset();
                        if (!forget) {
                            this.posted.push(message)
                        }
                        this.Suspend();
                        this.posting = true;
                        var result = this.listeners.Execute(message);
                        if (ISCALLBACK(result) && !result.called) {
                            WAITFOR(result, this)
                        }
                        this.Resume();
                        delete this.posting;
                        if (!this.pending) {
                            this.call()
                        }
                    }
                    return callback
                },
                Clear: function(callback) {
                    callback = USING(callback);
                    if (this.posting || this.pending) {
                        callback = this.Push(["Clear", this, callback])
                    } else {
                        this.posted = [];
                        callback()
                    }
                    return callback
                },
                call: function() {
                    this.callback(this);
                    this.Process()
                },
                Interest: function(callback, ignorePast, priority) {
                    callback = USING(callback);
                    this.listeners.Add(callback, priority);
                    if (!ignorePast) {
                        for (var i = 0, m = this.posted.length; i < m; i++) {
                            callback.reset();
                            var result = callback(this.posted[i]);
                            if (ISCALLBACK(result) && i === this.posted.length - 1) {
                                WAITFOR(result, this)
                            }
                        }
                    }
                    return callback
                },
                NoInterest: function(callback) {
                    this.listeners.Remove(callback)
                },
                MessageHook: function(msg, callback, priority) {
                    callback = USING(callback);
                    if (!this.hooks) {
                        this.hooks = {};
                        this.Interest(["ExecuteHooks", this])
                    }
                    if (!this.hooks[msg]) {
                        this.hooks[msg] = HOOKS(true)
                    }
                    this.hooks[msg].Add(callback, priority);
                    for (var i = 0, m = this.posted.length; i < m; i++) {
                        if (this.posted[i] == msg) {
                            callback.reset();
                            callback(this.posted[i])
                        }
                    }
                    return callback
                },
                ExecuteHooks: function(msg, more) {
                    var type = ((msg instanceof Array) ? msg[0] : msg);
                    if (!this.hooks[type]) {
                        return null
                    }
                    return this.hooks[type].Execute(msg)
                }
            }, {
                signals: {},
                find: function(name) {
                    if (!SIGNAL.signals[name]) {
                        SIGNAL.signals[name] = new SIGNAL(name)
                    }
                    return SIGNAL.signals[name]
                }
            });
            BASE.Callback = BASE.CallBack = USING;
            BASE.Callback.Delay = DELAY;
            BASE.Callback.After = AFTER;
            BASE.Callback.Queue = QUEUE;
            BASE.Callback.Signal = SIGNAL.find;
            BASE.Callback.Hooks = HOOKS;
            BASE.Callback.ExecuteHooks = EXECUTEHOOKS
        })("MathJax");
        (function(d) {
            var a = window[d];
            if (!a) {
                a = window[d] = {}
            }
            var c = (navigator.vendor === "Apple Computer, Inc." && typeof navigator.vendorSub === "undefined");
            var f = 0;
            var g = function(h) {
                if (document.styleSheets && document.styleSheets.length > f) {
                    f = document.styleSheets.length
                }
                if (!h) {
                    h = (document.getElementsByTagName("head"))[0];
                    if (!h) {
                        h = document.body
                    }
                }
                return h
            };
            var e = [];
            var b = function() {
                for (var j = 0, h = e.length; j < h; j++) {
                    a.Ajax.head.removeChild(e[j])
                }
                e = []
            };
            a.Ajax = {
                loaded: {},
                loading: {},
                loadHooks: {},
                timeout: 15 * 1000,
                styleDelay: 1,
                config: {
                    root: ""
                },
                STATUS: {
                    OK: 1,
                    ERROR: -1
                },
                rootPattern: new RegExp("^\\[" + d + "\\]"),
                fileURL: function(h) {
                    return h.replace(this.rootPattern, this.config.root)
                },
                Require: function(j, m) {
                    m = a.Callback(m);
                    var k;
                    if (j instanceof Object) {
                        for (var h in j) {
                            if (j.hasOwnProperty(h)) {
                                k = h.toUpperCase();
                                j = j[h]
                            }
                        }
                    } else {
                        k = j.split(/\./).pop().toUpperCase()
                    }
                    j = this.fileURL(j);
                    if (this.loaded[j]) {
                        m(this.loaded[j])
                    } else {
                        var l = {};
                        l[k] = j;
                        this.Load(l, m)
                    }
                    return m
                },
                Load: function(j, l) {
                    l = a.Callback(l);
                    var k;
                    if (j instanceof Object) {
                        for (var h in j) {
                            if (j.hasOwnProperty(h)) {
                                k = h.toUpperCase();
                                j = j[h]
                            }
                        }
                    } else {
                        k = j.split(/\./).pop().toUpperCase()
                    }
                    j = this.fileURL(j);
                    if (this.loading[j]) {
                        this.addHook(j, l)
                    } else {
                        this.head = g(this.head);
                        if (this.loader[k]) {
                            this.loader[k].call(this, j, l)
                        } else {
                            throw Error("Can't load files of type " + k)
                        }
                    }
                    return l
                },
                LoadHook: function(k, l, j) {
                    l = a.Callback(l);
                    if (k instanceof Object) {
                        for (var h in k) {
                            if (k.hasOwnProperty(h)) {
                                k = k[h]
                            }
                        }
                    }
                    k = this.fileURL(k);
                    if (this.loaded[k]) {
                        l(this.loaded[k])
                    } else {
                        this.addHook(k, l, j)
                    }
                    return l
                },
                addHook: function(i, j, h) {
                    if (!this.loadHooks[i]) {
                        this.loadHooks[i] = MathJax.Callback.Hooks()
                    }
                    this.loadHooks[i].Add(j, h)
                },
                Preloading: function() {
                    for (var k = 0, h = arguments.length; k < h; k++) {
                        var j = this.fileURL(arguments[k]);
                        if (!this.loading[j]) {
                            this.loading[j] = {
                                preloaded: true
                            }
                        }
                    }
                },
                loader: {
                    JS: function(i, k) {
                        var h = document.createElement("script");
                        var j = a.Callback(["loadTimeout", this, i]);
                        this.loading[i] = {
                            callback: k,
                            timeout: setTimeout(j, this.timeout),
                            status: this.STATUS.OK,
                            script: h
                        };
                        this.loading[i].message = a.Message.File(i);
                        h.onerror = j;
                        h.type = "text/javascript";
                        h.src = i;
                        this.head.appendChild(h)
                    },
                    CSS: function(h, j) {
                        var i = document.createElement("link");
                        i.rel = "stylesheet";
                        i.type = "text/css";
                        i.href = h;
                        this.loading[h] = {
                            callback: j,
                            message: a.Message.File(h),
                            status: this.STATUS.OK
                        };
                        this.head.appendChild(i);
                        this.timer.create.call(this, [this.timer.file, h], i)
                    }
                },
                timer: {
                    create: function(i, h) {
                        i = a.Callback(i);
                        if (h.nodeName === "STYLE" && h.styleSheet && typeof(h.styleSheet.cssText) !== "undefined") {
                            i(this.STATUS.OK)
                        } else {
                            if (window.chrome && typeof(window.sessionStorage) !== "undefined" && h.nodeName === "STYLE") {
                                i(this.STATUS.OK)
                            } else {
                                if (c) {
                                    this.timer.start(this, [this.timer.checkSafari2, f++, i], this.styleDelay)
                                } else {
                                    this.timer.start(this, [this.timer.checkLength, h, i], this.styleDelay)
                                }
                            }
                        }
                        return i
                    },
                    start: function(i, h, j, k) {
                        h = a.Callback(h);
                        h.execute = this.execute;
                        h.time = this.time;
                        h.STATUS = i.STATUS;
                        h.timeout = k || i.timeout;
                        h.delay = h.total = 0;
                        if (j) {
                            setTimeout(h, j)
                        } else {
                            h()
                        }
                    },
                    time: function(h) {
                        this.total += this.delay;
                        this.delay = Math.floor(this.delay * 1.05 + 5);
                        if (this.total >= this.timeout) {
                            h(this.STATUS.ERROR);
                            return 1
                        }
                        return 0
                    },
                    file: function(i, h) {
                        if (h < 0) {
                            a.Ajax.loadTimeout(i)
                        } else {
                            a.Ajax.loadComplete(i)
                        }
                    },
                    execute: function() {
                        this.hook.call(this.object, this, this.data[0], this.data[1])
                    },
                    checkSafari2: function(h, i, j) {
                        if (h.time(j)) {
                            return
                        }
                        if (document.styleSheets.length > i && document.styleSheets[i].cssRules && document.styleSheets[i].cssRules.length) {
                            j(h.STATUS.OK)
                        } else {
                            setTimeout(h, h.delay)
                        }
                    },
                    checkLength: function(h, k, m) {
                        if (h.time(m)) {
                            return
                        }
                        var l = 0;
                        var i = (k.sheet || k.styleSheet);
                        try {
                            if ((i.cssRules || i.rules || []).length > 0) {
                                l = 1
                            }
                        } catch (j) {
                            if (j.message.match(/protected variable|restricted URI/)) {
                                l = 1
                            } else {
                                if (j.message.match(/Security error/)) {
                                    l = 1
                                }
                            }
                        }
                        if (l) {
                            setTimeout(a.Callback([m, h.STATUS.OK]), 0)
                        } else {
                            setTimeout(h, h.delay)
                        }
                    }
                },
                loadComplete: function(h) {
                    h = this.fileURL(h);
                    var i = this.loading[h];
                    if (i && !i.preloaded) {
                        a.Message.Clear(i.message);
                        clearTimeout(i.timeout);
                        if (i.script) {
                            if (e.length === 0) {
                                setTimeout(b, 0)
                            }
                            e.push(i.script)
                        }
                        this.loaded[h] = i.status;
                        delete this.loading[h];
                        this.addHook(h, i.callback)
                    } else {
                        if (i) {
                            delete this.loading[h]
                        }
                        this.loaded[h] = this.STATUS.OK;
                        i = {
                            status: this.STATUS.OK
                        }
                    }
                    if (!this.loadHooks[h]) {
                        return null
                    }
                    return this.loadHooks[h].Execute(i.status)
                },
                loadTimeout: function(h) {
                    if (this.loading[h].timeout) {
                        clearTimeout(this.loading[h].timeout)
                    }
                    this.loading[h].status = this.STATUS.ERROR;
                    this.loadError(h);
                    this.loadComplete(h)
                },
                loadError: function(h) {
                    a.Message.Set(["LoadFailed", "File failed to load: %1", h], null, 2000);
                    a.Hub.signal.Post(["file load error", h])
                },
                Styles: function(j, k) {
                    var h = this.StyleString(j);
                    if (h === "") {
                        k = a.Callback(k);
                        k()
                    } else {
                        var i = document.createElement("style");
                        i.type = "text/css";
                        this.head = g(this.head);
                        this.head.appendChild(i);
                        if (i.styleSheet && typeof(i.styleSheet.cssText) !== "undefined") {
                            i.styleSheet.cssText = h
                        } else {
                            i.appendChild(document.createTextNode(h))
                        }
                        k = this.timer.create.call(this, k, i)
                    }
                    return k
                },
                StyleString: function(m) {
                    if (typeof(m) === "string") {
                        return m
                    }
                    var j = "",
                        n, l;
                    for (n in m) {
                        if (m.hasOwnProperty(n)) {
                            if (typeof m[n] === "string") {
                                j += n + " {" + m[n] + "}\n"
                            } else {
                                if (m[n] instanceof Array) {
                                    for (var k = 0; k < m[n].length; k++) {
                                        l = {};
                                        l[n] = m[n][k];
                                        j += this.StyleString(l)
                                    }
                                } else {
                                    if (n.substr(0, 6) === "@media") {
                                        j += n + " {" + this.StyleString(m[n]) + "}\n"
                                    } else {
                                        if (m[n] != null) {
                                            l = [];
                                            for (var h in m[n]) {
                                                if (m[n].hasOwnProperty(h)) {
                                                    if (m[n][h] != null) {
                                                        l[l.length] = h + ": " + m[n][h]
                                                    }
                                                }
                                            }
                                            j += n + " {" + l.join("; ") + "}\n"
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return j
                }
            }
        })("MathJax");
        MathJax.HTML = {
            Element: function(c, e, d) {
                var f = document.createElement(c);
                if (e) {
                    if (e.style) {
                        var b = e.style;
                        e.style = {};
                        for (var g in b) {
                            if (b.hasOwnProperty(g)) {
                                e.style[g.replace(/-([a-z])/g, this.ucMatch)] = b[g]
                            }
                        }
                    }
                    MathJax.Hub.Insert(f, e)
                }
                if (d) {
                    if (!(d instanceof Array)) {
                        d = [d]
                    }
                    for (var a = 0; a < d.length; a++) {
                        if (d[a] instanceof Array) {
                            f.appendChild(this.Element(d[a][0], d[a][1], d[a][2]))
                        } else {
                            if (c === "script") {
                                this.setScript(f, d[a])
                            } else {
                                f.appendChild(document.createTextNode(d[a]))
                            }
                        }
                    }
                }
                return f
            },
            ucMatch: function(a, b) {
                return b.toUpperCase()
            },
            addElement: function(b, a, d, c) {
                return b.appendChild(this.Element(a, d, c))
            },
            TextNode: function(a) {
                return document.createTextNode(a)
            },
            addText: function(a, b) {
                return a.appendChild(this.TextNode(b))
            },
            setScript: function(a, b) {
                if (this.setScriptBug) {
                    a.text = b
                } else {
                    while (a.firstChild) {
                        a.removeChild(a.firstChild)
                    }
                    this.addText(a, b)
                }
            },
            getScript: function(a) {
                var b = (a.text === "" ? a.innerHTML : a.text);
                return b.replace(/^\s+/, "").replace(/\s+$/, "")
            },
            Cookie: {
                prefix: "mjx",
                expires: 365,
                Set: function(a, e) {
                    var d = [];
                    if (e) {
                        for (var g in e) {
                            if (e.hasOwnProperty(g)) {
                                d.push(g + ":" + e[g].toString().replace(/&/g, "&&"))
                            }
                        }
                    }
                    var b = this.prefix + "." + a + "=" + escape(d.join("&;"));
                    if (this.expires) {
                        var f = new Date();
                        f.setDate(f.getDate() + this.expires);
                        b += "; expires=" + f.toGMTString()
                    }
                    try {
                        document.cookie = b + "; path=/"
                    } catch (c) {}
                },
                Get: function(c, h) {
                    if (!h) {
                        h = {}
                    }
                    var g = new RegExp("(?:^|;\\s*)" + this.prefix + "\\." + c + "=([^;]*)(?:;|$)");
                    var b = g.exec(document.cookie);
                    if (b && b[1] !== "") {
                        var e = unescape(b[1]).split("&;");
                        for (var d = 0, a = e.length; d < a; d++) {
                            b = e[d].match(/([^:]+):(.*)/);
                            var f = b[2].replace(/&&/g, "&");
                            if (f === "true") {
                                f = true
                            } else {
                                if (f === "false") {
                                    f = false
                                } else {
                                    if (f.match(/^-?(\d+(\.\d+)?|\.\d+)$/)) {
                                        f = parseFloat(f)
                                    }
                                }
                            }
                            h[b[1]] = f
                        }
                    }
                    return h
                }
            }
        };
        MathJax.Localization = {
            locale: "en",
            directory: "[MathJax]/localization",
            strings: {
                en: {
                    menuTitle: "English",
                    isLoaded: true
                },
                de: {
                    menuTitle: "Deutsch"
                },
                fr: {
                    menuTitle: "Fran\u00E7ais"
                }
            },
            pattern: /%(\d+|\{\d+\}|\{[a-z]+:\%\d+(?:\|(?:%\{\d+\}|%.|[^\}])*)+\}|.)/g,
            SPLIT: ("axb".split(/(x)/).length === 3 ? function(a, b) {
                return a.split(b)
            } : function(c, e) {
                var a = [],
                    b, d = 0;
                e.lastIndex = 0;
                while (b = e.exec(c)) {
                    a.push(c.substr(d, b.index));
                    a.push.apply(a, b.slice(1));
                    d = b.index + b[0].length
                }
                a.push(c.substr(d));
                return a
            }),
            _: function(b, a) {
                if (a instanceof Array) {
                    return this.processSnippet(b, a)
                }
                return this.processString(this.lookupPhrase(b, a), [].slice.call(arguments, 2))
            },
            processString: function(l, o, g) {
                var j, e;
                for (j = 0, e = o.length; j < e; j++) {
                    if (g && o[j] instanceof Array) {
                        o[j] = this.processSnippet(g, o[j])
                    }
                }
                var f = this.SPLIT(l, this.pattern);
                for (j = 1, e = f.length; j < e; j += 2) {
                    var p = f[j].charAt(0);
                    if (p >= "0" && p <= "9") {
                        f[j] = o[f[j] - 1];
                        if (typeof f[j] === "number") {
                            f[j] = this.number(f[j])
                        }
                    } else {
                        if (p === "{") {
                            p = f[j].substr(1);
                            if (p >= "0" && p <= "9") {
                                f[j] = o[f[j].substr(1, f[j].length - 2) - 1];
                                if (typeof f[j] === "number") {
                                    f[j] = this.number(f[j])
                                }
                            } else {
                                var k = f[j].match(/^\{([a-z]+):%(\d+)\|(.*)\}$/);
                                if (k) {
                                    if (k[1] === "plural") {
                                        var d = o[k[2] - 1];
                                        if (typeof d === "undefined") {
                                            f[j] = "???"
                                        } else {
                                            d = this.plural(d) - 1;
                                            var h = k[3].replace(/(^|[^%])(%%)*%\|/g, "$1$2%\uEFEF").split(/\|/);
                                            if (d >= 0 && d < h.length) {
                                                f[j] = this.processString(h[d].replace(/\uEFEF/g, "|"), o, g)
                                            } else {
                                                f[j] = "???"
                                            }
                                        }
                                    } else {
                                        f[j] = "%" + f[j]
                                    }
                                }
                            }
                        }
                    }
                    if (f[j] == null) {
                        f[j] = "???"
                    }
                }
                if (!g) {
                    return f.join("")
                }
                var a = [],
                    b = "";
                for (j = 0; j < e; j++) {
                    b += f[j];
                    j++;
                    if (j < e) {
                        if (f[j] instanceof Array) {
                            a.push(b);
                            a = a.concat(f[j]);
                            b = ""
                        } else {
                            b += f[j]
                        }
                    }
                }
                if (b !== "") {
                    a.push(b)
                }
                return a
            },
            processSnippet: function(g, e) {
                var c = [];
                for (var d = 0, b = e.length; d < b; d++) {
                    if (e[d] instanceof Array) {
                        var f = e[d];
                        if (typeof f[1] === "string") {
                            var h = f[0];
                            if (!(h instanceof Array)) {
                                h = [g, h]
                            }
                            var a = this.lookupPhrase(h, f[1]);
                            c = c.concat(this.processMarkdown(a, f.slice(2), g))
                        } else {
                            if (f[1] instanceof Array) {
                                c = c.concat(this.processSnippet.apply(this, f))
                            } else {
                                if (f.length >= 3) {
                                    c.push([f[0], f[1], this.processSnippet(g, f[2])])
                                } else {
                                    c.push(e[d])
                                }
                            }
                        }
                    } else {
                        c.push(e[d])
                    }
                }
                return c
            },
            markdownPattern: /(%.)|(\*{1,3})((?:%.|.)+?)\2|(`+)((?:%.|.)+?)\4|\[((?:%.|.)+?)\]\(([^\s\)]+)\)/,
            processMarkdown: function(b, h, d) {
                var j = [],
                    e;
                var c = b.split(this.markdownPattern);
                var g = c[0];
                for (var f = 1, a = c.length; f < a; f += 8) {
                    if (c[f + 1]) {
                        e = this.processString(c[f + 2], h, d);
                        if (!(e instanceof Array)) {
                            e = [e]
                        }
                        e = [
                            ["b", "i", "i"][c[f + 1].length - 1], {},
                            e
                        ];
                        if (c[f + 1].length === 3) {
                            e = ["b", {}, e]
                        }
                    } else {
                        if (c[f + 3]) {
                            e = this.processString(c[f + 4].replace(/^\s/, "").replace(/\s$/, ""), h, d);
                            if (!(e instanceof Array)) {
                                e = [e]
                            }
                            e = ["code", {}, e]
                        } else {
                            if (c[f + 5]) {
                                e = this.processString(c[f + 5], h, d);
                                if (!(e instanceof Array)) {
                                    e = [e]
                                }
                                e = ["a", {
                                    href: this.processString(c[f + 6], h),
                                    target: "_blank"
                                }, e]
                            } else {
                                g += c[f];
                                e = null
                            }
                        }
                    }
                    if (e) {
                        j = this.concatString(j, g, h, d);
                        j.push(e);
                        g = ""
                    }
                    if (c[f + 7] !== "") {
                        g += c[f + 7]
                    }
                }
                j = this.concatString(j, g, h, d);
                return j
            },
            concatString: function(a, c, b, d) {
                if (c != "") {
                    c = this.processString(c, b, d);
                    if (!(c instanceof Array)) {
                        c = [c]
                    }
                    a = a.concat(c)
                }
                return a
            },
            lookupPhrase: function(f, a, d) {
                if (!d) {
                    d = "_"
                }
                if (f instanceof Array) {
                    d = (f[0] || "_");
                    f = (f[1] || "")
                }
                var c = this.loadDomain(d);
                if (c) {
                    MathJax.Hub.RestartAfter(c)
                }
                var b = this.strings[this.locale];
                if (b) {
                    if (b.domains && d in b.domains) {
                        var e = b.domains[d];
                        if (e.strings && f in e.strings) {
                            a = e.strings[f]
                        }
                    }
                }
                return a
            },
            loadFile: function(b, d, e) {
                e = MathJax.Callback(e || {});
                b = (d.file || b);
                if (!b.match(/\.js$/)) {
                    b += ".js"
                }
                if (!b.match(/^([a-z]+:|\[MathJax\])/)) {
                    var a = (this.strings[this.locale].directory || this.directory + "/" + this.locale || "[MathJax]/localization/" + this.locale);
                    b = a + "/" + b
                }
                var c = MathJax.Ajax.Require(b, function() {
                    d.isLoaded = true;
                    return e()
                });
                return (c.called ? null : c)
            },
            loadDomain: function(c, e) {
                var b, a = this.strings[this.locale];
                if (a) {
                    if (!a.isLoaded) {
                        b = this.loadFile(this.locale, a);
                        if (b) {
                            return MathJax.Callback.Queue(b, ["loadDomain", this, c]).Push(e)
                        }
                    }
                    if (a.domains && c in a.domains) {
                        var d = a.domains[c];
                        if (!d.isLoaded) {
                            b = this.loadFile(c, d);
                            if (b) {
                                return MathJax.Callback.Queue(b).Push(e)
                            }
                        }
                    }
                }
                return MathJax.Callback(e)()
            },
            Try: function(a) {
                a = MathJax.Callback(a);
                a.autoReset = true;
                try {
                    a()
                } catch (b) {
                    if (!b.restart) {
                        throw b
                    }
                    MathJax.Callback.After(["Try", this, a], b.restart)
                }
            },
            setLocale: function(a) {
                if (this.strings[a]) {
                    this.locale = a
                }
                if (MathJax.Menu) {
                    this.loadDomain("MathMenu")
                }
            },
            addTranslation: function(b, e, c) {
                var d = this.strings[b],
                    a = false;
                if (!d) {
                    d = this.strings[b] = {};
                    a = true
                }
                if (!d.domains) {
                    d.domains = {}
                }
                if (e) {
                    if (!d.domains[e]) {
                        d.domains[e] = {}
                    }
                    d = d.domains[e]
                }
                MathJax.Hub.Insert(d, c);
                if (a && MathJax.Menu.menu) {
                    MathJax.Menu.CreateLocaleMenu()
                }
            },
            setCSS: function(b) {
                var a = this.strings[this.locale];
                if (a) {
                    if (a.fontFamily) {
                        b.style.fontFamily = a.fontFamily
                    }
                    if (a.fontDirection) {
                        b.style.direction = a.fontDirection;
                        if (a.fontDirection === "rtl") {
                            b.style.textAlign = "right"
                        }
                    }
                }
                return b
            },
            fontFamily: function() {
                var a = this.strings[this.locale];
                return (a ? a.fontFamily : null)
            },
            fontDirection: function() {
                var a = this.strings[this.locale];
                return (a ? a.fontDirection : null)
            },
            plural: function(b) {
                var a = this.strings[this.locale];
                if (a && a.plural) {
                    return a.plural(b)
                }
                if (b == 1) {
                    return 1
                }
                return 2
            },
            number: function(b) {
                var a = this.strings[this.locale];
                if (a && a.number) {
                    return a.number(b)
                }
                return b
            }
        };
        MathJax.Message = {
            ready: false,
            log: [{}],
            current: null,
            textNodeBug: (navigator.vendor === "Apple Computer, Inc." && typeof navigator.vendorSub === "undefined") || (window.hasOwnProperty && window.hasOwnProperty("konqueror")),
            styles: {
                "#MathJax_Message": {
                    position: "fixed",
                    left: "1px",
                    bottom: "2px",
                    "background-color": "#E6E6E6",
                    border: "1px solid #959595",
                    margin: "0px",
                    padding: "2px 8px",
                    "z-index": "102",
                    color: "black",
                    "font-size": "80%",
                    width: "auto",
                    "white-space": "nowrap"
                },
                "#MathJax_MSIE_Frame": {
                    position: "absolute",
                    top: 0,
                    left: 0,
                    width: "0px",
                    "z-index": 101,
                    border: "0px",
                    margin: "0px",
                    padding: "0px"
                }
            },
            browsers: {
                MSIE: function(a) {
                    MathJax.Hub.config.styles["#MathJax_Message"].position = "absolute";
                    MathJax.Message.quirks = (document.compatMode === "BackCompat")
                },
                Chrome: function(a) {
                    MathJax.Hub.config.styles["#MathJax_Message"].bottom = "1.5em";
                    MathJax.Hub.config.styles["#MathJax_Message"].left = "1em"
                }
            },
            Init: function(a) {
                if (a) {
                    this.ready = true
                }
                if (!document.body || !this.ready) {
                    return false
                }
                if (this.div && this.div.parentNode == null) {
                    this.div = document.getElementById("MathJax_Message");
                    if (this.div) {
                        this.text = this.div.firstChild
                    }
                }
                if (!this.div) {
                    var b = document.body;
                    if (MathJax.Hub.Browser.isMSIE) {
                        b = this.frame = this.addDiv(document.body);
                        b.removeAttribute("id");
                        b.style.position = "absolute";
                        b.style.border = b.style.margin = b.style.padding = "0px";
                        b.style.zIndex = "101";
                        b.style.height = "0px";
                        b = this.addDiv(b);
                        b.id = "MathJax_MSIE_Frame";
                        window.attachEvent("onscroll", this.MoveFrame);
                        window.attachEvent("onresize", this.MoveFrame);
                        this.MoveFrame()
                    }
                    this.div = this.addDiv(b);
                    this.div.style.display = "none";
                    this.text = this.div.appendChild(document.createTextNode(""))
                }
                return true
            },
            addDiv: function(a) {
                var b = document.createElement("div");
                b.id = "MathJax_Message";
                if (a.firstChild) {
                    a.insertBefore(b, a.firstChild)
                } else {
                    a.appendChild(b)
                }
                return b
            },
            MoveFrame: function() {
                var a = (MathJax.Message.quirks ? document.body : document.documentElement);
                var b = MathJax.Message.frame;
                b.style.left = a.scrollLeft + "px";
                b.style.top = a.scrollTop + "px";
                b.style.width = a.clientWidth + "px";
                b = b.firstChild;
                b.style.height = a.clientHeight + "px"
            },
            localize: function(a) {
                return MathJax.Localization._(a, a)
            },
            filterText: function(a, c, b) {
                if (MathJax.Hub.config.messageStyle === "simple") {
                    if (b === "LoadFile") {
                        if (!this.loading) {
                            this.loading = this.localize("Loading") + " "
                        }
                        a = this.loading;
                        this.loading += "."
                    } else {
                        if (b === "ProcessMath") {
                            if (!this.processing) {
                                this.processing = this.localize("Processing") + " "
                            }
                            a = this.processing;
                            this.processing += "."
                        } else {
                            if (b === "TypesetMath") {
                                if (!this.typesetting) {
                                    this.typesetting = this.localize("Typesetting") + " "
                                }
                                a = this.typesetting;
                                this.typesetting += "."
                            }
                        }
                    }
                }
                return a
            },
            Set: function(c, e, b) {
                if (e == null) {
                    e = this.log.length;
                    this.log[e] = {}
                }
                var d = "";
                if (c instanceof Array) {
                    d = c[0];
                    if (d instanceof Array) {
                        d = d[1]
                    }
                    try {
                        c = MathJax.Localization._.apply(MathJax.Localization, c)
                    } catch (a) {
                        if (!a.restart) {
                            throw a
                        }
                        if (!a.restart.called) {
                            if (this.log[e].restarted == null) {
                                this.log[e].restarted = 0
                            }
                            this.log[e].restarted++;
                            delete this.log[e].cleared;
                            MathJax.Callback.After(["Set", this, c, e, b], a.restart);
                            return e
                        }
                    }
                }
                if (this.timer) {
                    clearTimeout(this.timer);
                    delete this.timer
                }
                this.log[e].text = c;
                this.log[e].filteredText = c = this.filterText(c, e, d);
                if (typeof(this.log[e].next) === "undefined") {
                    this.log[e].next = this.current;
                    if (this.current != null) {
                        this.log[this.current].prev = e
                    }
                    this.current = e
                }
                if (this.current === e && MathJax.Hub.config.messageStyle !== "none") {
                    if (this.Init()) {
                        if (this.textNodeBug) {
                            this.div.innerHTML = c
                        } else {
                            this.text.nodeValue = c
                        }
                        this.div.style.display = "";
                        if (this.status) {
                            window.status = "";
                            delete this.status
                        }
                    } else {
                        window.status = c;
                        this.status = true
                    }
                }
                if (this.log[e].restarted) {
                    if (this.log[e].cleared) {
                        b = 0
                    }
                    if (--this.log[e].restarted === 0) {
                        delete this.log[e].cleared
                    }
                }
                if (b) {
                    setTimeout(MathJax.Callback(["Clear", this, e]), b)
                } else {
                    if (b == 0) {
                        this.Clear(e, 0)
                    }
                }
                return e
            },
            Clear: function(b, a) {
                if (this.log[b].prev != null) {
                    this.log[this.log[b].prev].next = this.log[b].next
                }
                if (this.log[b].next != null) {
                    this.log[this.log[b].next].prev = this.log[b].prev
                }
                if (this.current === b) {
                    this.current = this.log[b].next;
                    if (this.text) {
                        if (this.div.parentNode == null) {
                            this.Init()
                        }
                        if (this.current == null) {
                            if (this.timer) {
                                clearTimeout(this.timer);
                                delete this.timer
                            }
                            if (a == null) {
                                a = 600
                            }
                            if (a === 0) {
                                this.Remove()
                            } else {
                                this.timer = setTimeout(MathJax.Callback(["Remove", this]), a)
                            }
                        } else {
                            if (MathJax.Hub.config.messageStyle !== "none") {
                                if (this.textNodeBug) {
                                    this.div.innerHTML = this.log[this.current].filteredText
                                } else {
                                    this.text.nodeValue = this.log[this.current].filteredText
                                }
                            }
                        }
                        if (this.status) {
                            window.status = "";
                            delete this.status
                        }
                    } else {
                        if (this.status) {
                            window.status = (this.current == null ? "" : this.log[this.current].text)
                        }
                    }
                }
                delete this.log[b].next;
                delete this.log[b].prev;
                delete this.log[b].filteredText;
                if (this.log[b].restarted) {
                    this.log[b].cleared = true
                }
            },
            Remove: function() {
                this.text.nodeValue = "";
                this.div.style.display = "none"
            },
            File: function(b) {
                var a = MathJax.Ajax.config.root;
                if (b.substr(0, a.length) === a) {
                    b = "[MathJax]" + b.substr(a.length)
                }
                return this.Set(["LoadFile", "Loading %1", b], null, null)
            },
            Log: function() {
                var b = [];
                for (var c = 1, a = this.log.length; c < a; c++) {
                    b[c] = this.log[c].text
                }
                return b.join("\n")
            }
        };
        MathJax.Hub = {
            config: {
                root: "",
                config: [],
                styleSheets: [],
                styles: {
                    ".MathJax_Preview": {
                        color: "#888"
                    }
                },
                jax: [],
                extensions: [],
                preJax: null,
                postJax: null,
                displayAlign: "center",
                displayIndent: "0",
                preRemoveClass: "MathJax_Preview",
                showProcessingMessages: true,
                messageStyle: "normal",
                delayStartupUntil: "none",
                skipStartupTypeset: false,
                elements: [],
                positionToHash: true,
                showMathMenu: true,
                showMathMenuMSIE: true,
                menuSettings: {
                    zoom: "None",
                    CTRL: false,
                    ALT: false,
                    CMD: false,
                    Shift: false,
                    discoverable: false,
                    zscale: "200%",
                    renderer: "",
                    font: "Auto",
                    context: "MathJax",
                    locale: "en",
                    mpContext: false,
                    mpMouse: false,
                    texHints: true
                },
                errorSettings: {
                    message: ["[", ["MathProcessingError", "Math Processing Error"], "]"],
                    style: {
                        color: "#CC0000",
                        "font-style": "italic"
                    }
                }
            },
            preProcessors: MathJax.Callback.Hooks(true),
            inputJax: {},
            outputJax: {
                order: {}
            },
            processUpdateTime: 250,
            processUpdateDelay: 10,
            signal: MathJax.Callback.Signal("Hub"),
            Config: function(a) {
                this.Insert(this.config, a);
                if (this.config.Augment) {
                    this.Augment(this.config.Augment)
                }
            },
            CombineConfig: function(c, f) {
                var b = this.config,
                    g, e;
                c = c.split(/\./);
                for (var d = 0, a = c.length; d < a; d++) {
                    g = c[d];
                    if (!b[g]) {
                        b[g] = {}
                    }
                    e = b;
                    b = b[g]
                }
                e[g] = b = this.Insert(f, b);
                return b
            },
            Register: {
                PreProcessor: function() {
                    MathJax.Hub.preProcessors.Add.apply(MathJax.Hub.preProcessors, arguments)
                },
                MessageHook: function() {
                    return MathJax.Hub.signal.MessageHook.apply(MathJax.Hub.signal, arguments)
                },
                StartupHook: function() {
                    return MathJax.Hub.Startup.signal.MessageHook.apply(MathJax.Hub.Startup.signal, arguments)
                },
                LoadHook: function() {
                    return MathJax.Ajax.LoadHook.apply(MathJax.Ajax, arguments)
                }
            },
            getAllJax: function(e) {
                var c = [],
                    b = this.elementScripts(e);
                for (var d = 0, a = b.length; d < a; d++) {
                    if (b[d].MathJax && b[d].MathJax.elementJax) {
                        c.push(b[d].MathJax.elementJax)
                    }
                }
                return c
            },
            getJaxByType: function(f, e) {
                var c = [],
                    b = this.elementScripts(e);
                for (var d = 0, a = b.length; d < a; d++) {
                    if (b[d].MathJax && b[d].MathJax.elementJax && b[d].MathJax.elementJax.mimeType === f) {
                        c.push(b[d].MathJax.elementJax)
                    }
                }
                return c
            },
            getJaxByInputType: function(f, e) {
                var c = [],
                    b = this.elementScripts(e);
                for (var d = 0, a = b.length; d < a; d++) {
                    if (b[d].MathJax && b[d].MathJax.elementJax && b[d].type && b[d].type.replace(/ *;(.|\s)*/, "") === f) {
                        c.push(b[d].MathJax.elementJax)
                    }
                }
                return c
            },
            getJaxFor: function(a) {
                if (typeof(a) === "string") {
                    a = document.getElementById(a)
                }
                if (a && a.MathJax) {
                    return a.MathJax.elementJax
                }
                if (a && a.isMathJax) {
                    while (a && !a.jaxID) {
                        a = a.parentNode
                    }
                    if (a) {
                        return MathJax.OutputJax[a.jaxID].getJaxFromMath(a)
                    }
                }
                return null
            },
            isJax: function(a) {
                if (typeof(a) === "string") {
                    a = document.getElementById(a)
                }
                if (a && a.isMathJax) {
                    return 1
                }
                if (a && a.tagName != null && a.tagName.toLowerCase() === "script") {
                    if (a.MathJax) {
                        return (a.MathJax.state === MathJax.ElementJax.STATE.PROCESSED ? 1 : -1)
                    }
                    if (a.type && this.inputJax[a.type.replace(/ *;(.|\s)*/, "")]) {
                        return -1
                    }
                }
                return 0
            },
            setRenderer: function(d, c) {
                if (!d) {
                    return
                }
                if (!MathJax.OutputJax[d]) {
                    this.config.menuSettings.renderer = "";
                    var b = "[MathJax]/jax/output/" + d + "/config.js";
                    return MathJax.Ajax.Require(b, ["setRenderer", this, d, c])
                } else {
                    this.config.menuSettings.renderer = d;
                    if (c == null) {
                        c = "jax/mml"
                    }
                    var a = this.outputJax;
                    if (a[c] && a[c].length) {
                        if (d !== a[c][0].id) {
                            a[c].unshift(MathJax.OutputJax[d]);
                            return this.signal.Post(["Renderer Selected", d])
                        }
                    }
                    return null
                }
            },
            Queue: function() {
                return this.queue.Push.apply(this.queue, arguments)
            },
            Typeset: function(e, f) {
                if (!MathJax.isReady) {
                    return null
                }
                var c = this.elementCallback(e, f);
                var b = MathJax.Callback.Queue();
                for (var d = 0, a = c.elements.length; d < a; d++) {
                    if (c.elements[d]) {
                        b.Push(["PreProcess", this, c.elements[d]], ["Process", this, c.elements[d]])
                    }
                }
                return b.Push(c.callback)
            },
            PreProcess: function(e, f) {
                var c = this.elementCallback(e, f);
                var b = MathJax.Callback.Queue();
                for (var d = 0, a = c.elements.length; d < a; d++) {
                    if (c.elements[d]) {
                        b.Push(["Post", this.signal, ["Begin PreProcess", c.elements[d]]], (arguments.callee.disabled ? {} : ["Execute", this.preProcessors, c.elements[d]]), ["Post", this.signal, ["End PreProcess", c.elements[d]]])
                    }
                }
                return b.Push(c.callback)
            },
            Process: function(a, b) {
                return this.takeAction("Process", a, b)
            },
            Update: function(a, b) {
                return this.takeAction("Update", a, b)
            },
            Reprocess: function(a, b) {
                return this.takeAction("Reprocess", a, b)
            },
            Rerender: function(a, b) {
                return this.takeAction("Rerender", a, b)
            },
            takeAction: function(g, e, h) {
                var c = this.elementCallback(e, h);
                var b = MathJax.Callback.Queue(["Clear", this.signal]);
                for (var d = 0, a = c.elements.length; d < a; d++) {
                    if (c.elements[d]) {
                        var f = {
                            scripts: [],
                            start: new Date().getTime(),
                            i: 0,
                            j: 0,
                            jax: {},
                            jaxIDs: []
                        };
                        b.Push(["Post", this.signal, ["Begin " + g, c.elements[d]]], ["Post", this.signal, ["Begin Math", c.elements[d], g]], ["prepareScripts", this, g, c.elements[d], f], ["Post", this.signal, ["Begin Math Input", c.elements[d], g]], ["processInput", this, f], ["Post", this.signal, ["End Math Input", c.elements[d], g]], ["prepareOutput", this, f, "preProcess"], ["Post", this.signal, ["Begin Math Output", c.elements[d], g]], ["processOutput", this, f], ["Post", this.signal, ["End Math Output", c.elements[d], g]], ["prepareOutput", this, f, "postProcess"], ["Post", this.signal, ["End Math", c.elements[d], g]], ["Post", this.signal, ["End " + g, c.elements[d]]])
                    }
                }
                return b.Push(c.callback)
            },
            scriptAction: {
                Process: function(a) {},
                Update: function(b) {
                    var a = b.MathJax.elementJax;
                    if (a && a.needsUpdate()) {
                        a.Remove(true);
                        b.MathJax.state = a.STATE.UPDATE
                    } else {
                        b.MathJax.state = a.STATE.PROCESSED
                    }
                },
                Reprocess: function(b) {
                    var a = b.MathJax.elementJax;
                    if (a) {
                        a.Remove(true);
                        b.MathJax.state = a.STATE.UPDATE
                    }
                },
                Rerender: function(b) {
                    var a = b.MathJax.elementJax;
                    if (a) {
                        a.Remove(true);
                        b.MathJax.state = a.STATE.OUTPUT
                    }
                }
            },
            prepareScripts: function(h, e, g) {
                if (arguments.callee.disabled) {
                    return
                }
                var b = this.elementScripts(e);
                var f = MathJax.ElementJax.STATE;
                for (var d = 0, a = b.length; d < a; d++) {
                    var c = b[d];
                    if (c.type && this.inputJax[c.type.replace(/ *;(.|\n)*/, "")]) {
                        if (c.MathJax) {
                            if (c.MathJax.elementJax && c.MathJax.elementJax.hover) {
                                MathJax.Extension.MathEvents.Hover.ClearHover(c.MathJax.elementJax)
                            }
                            if (c.MathJax.state !== f.PENDING) {
                                this.scriptAction[h](c)
                            }
                        }
                        if (!c.MathJax) {
                            c.MathJax = {
                                state: f.PENDING
                            }
                        }
                        if (c.MathJax.state !== f.PROCESSED) {
                            g.scripts.push(c)
                        }
                    }
                }
            },
            checkScriptSiblings: function(a) {
                if (a.MathJax.checked) {
                    return
                }
                var b = this.config,
                    f = a.previousSibling;
                if (f && f.nodeName === "#text") {
                    var d, e, c = a.nextSibling;
                    if (c && c.nodeName !== "#text") {
                        c = null
                    }
                    if (b.preJax) {
                        if (typeof(b.preJax) === "string") {
                            b.preJax = new RegExp(b.preJax + "$")
                        }
                        d = f.nodeValue.match(b.preJax)
                    }
                    if (b.postJax && c) {
                        if (typeof(b.postJax) === "string") {
                            b.postJax = new RegExp("^" + b.postJax)
                        }
                        e = c.nodeValue.match(b.postJax)
                    }
                    if (d && (!b.postJax || e)) {
                        f.nodeValue = f.nodeValue.replace(b.preJax, (d.length > 1 ? d[1] : ""));
                        f = null
                    }
                    if (e && (!b.preJax || d)) {
                        c.nodeValue = c.nodeValue.replace(b.postJax, (e.length > 1 ? e[1] : ""))
                    }
                    if (f && !f.nodeValue.match(/\S/)) {
                        f = f.previousSibling
                    }
                }
                if (b.preRemoveClass && f && f.className === b.preRemoveClass) {
                    a.MathJax.preview = f
                }
                a.MathJax.checked = 1
            },
            processInput: function(a) {
                var b, i = MathJax.ElementJax.STATE;
                var h, e, d = a.scripts.length;
                try {
                    while (a.i < d) {
                        h = a.scripts[a.i];
                        if (!h) {
                            a.i++;
                            continue
                        }
                        e = h.previousSibling;
                        if (e && e.className === "MathJax_Error") {
                            e.parentNode.removeChild(e)
                        }
                        if (!h.MathJax || h.MathJax.state === i.PROCESSED) {
                            a.i++;
                            continue
                        }
                        if (!h.MathJax.elementJax || h.MathJax.state === i.UPDATE) {
                            this.checkScriptSiblings(h);
                            var g = h.type.replace(/ *;(.|\s)*/, "");
                            b = this.inputJax[g].Process(h, a);
                            if (typeof b === "function") {
                                if (b.called) {
                                    continue
                                }
                                this.RestartAfter(b)
                            }
                            b.Attach(h, this.inputJax[g].id);
                            this.saveScript(b, a, h, i)
                        } else {
                            if (h.MathJax.state === i.OUTPUT) {
                                this.saveScript(h.MathJax.elementJax, a, h, i)
                            }
                        }
                        a.i++;
                        var c = new Date().getTime();
                        if (c - a.start > this.processUpdateTime && a.i < a.scripts.length) {
                            a.start = c;
                            this.RestartAfter(MathJax.Callback.Delay(1))
                        }
                    }
                } catch (f) {
                    return this.processError(f, a, "Input")
                }
                if (a.scripts.length && this.config.showProcessingMessages) {
                    MathJax.Message.Set(["ProcessMath", "Processing math: %1%%", 100], 0)
                }
                a.start = new Date().getTime();
                a.i = a.j = 0;
                return null
            },
            saveScript: function(a, d, b, c) {
                if (!this.outputJax[a.mimeType]) {
                    b.MathJax.state = c.UPDATE;
                    throw Error("No output jax registered for " + a.mimeType)
                }
                a.outputJax = this.outputJax[a.mimeType][0].id;
                if (!d.jax[a.outputJax]) {
                    if (d.jaxIDs.length === 0) {
                        d.jax[a.outputJax] = d.scripts
                    } else {
                        if (d.jaxIDs.length === 1) {
                            d.jax[d.jaxIDs[0]] = d.scripts.slice(0, d.i)
                        }
                        d.jax[a.outputJax] = []
                    }
                    d.jaxIDs.push(a.outputJax)
                }
                if (d.jaxIDs.length > 1) {
                    d.jax[a.outputJax].push(b)
                }
                b.MathJax.state = c.OUTPUT
            },
            prepareOutput: function(c, f) {
                while (c.j < c.jaxIDs.length) {
                    var e = c.jaxIDs[c.j],
                        d = MathJax.OutputJax[e];
                    if (d[f]) {
                        try {
                            var a = d[f](c);
                            if (typeof a === "function") {
                                if (a.called) {
                                    continue
                                }
                                this.RestartAfter(a)
                            }
                        } catch (b) {
                            if (!b.restart) {
                                MathJax.Message.Set(["PrepError", "Error preparing %1 output (%2)", e, f], null, 600);
                                MathJax.Hub.lastPrepError = b;
                                c.j++
                            }
                            return MathJax.Callback.After(["prepareOutput", this, c, f], b.restart)
                        }
                    }
                    c.j++
                }
                return null
            },
            processOutput: function(h) {
                var b, g = MathJax.ElementJax.STATE,
                    d, a = h.scripts.length;
                try {
                    while (h.i < a) {
                        d = h.scripts[h.i];
                        if (!d || !d.MathJax || d.MathJax.error) {
                            h.i++;
                            continue
                        }
                        var c = d.MathJax.elementJax;
                        if (!c) {
                            h.i++;
                            continue
                        }
                        b = MathJax.OutputJax[c.outputJax].Process(d, h);
                        d.MathJax.state = g.PROCESSED;
                        h.i++;
                        if (d.MathJax.preview) {
                            d.MathJax.preview.innerHTML = ""
                        }
                        this.signal.Post(["New Math", c.inputID]);
                        var e = new Date().getTime();
                        if (e - h.start > this.processUpdateTime && h.i < h.scripts.length) {
                            h.start = e;
                            this.RestartAfter(MathJax.Callback.Delay(this.processUpdateDelay))
                        }
                    }
                } catch (f) {
                    return this.processError(f, h, "Output")
                }
                if (h.scripts.length && this.config.showProcessingMessages) {
                    MathJax.Message.Set(["TypesetMath", "Typesetting math: %1%%", 100], 0);
                    MathJax.Message.Clear(0)
                }
                h.i = h.j = 0;
                return null
            },
            processMessage: function(d, b) {
                var a = Math.floor(d.i / (d.scripts.length) * 100);
                var c = (b === "Output" ? ["TypesetMath", "Typesetting math: %1%%"] : ["ProcessMath", "Processing math: %1%%"]);
                if (this.config.showProcessingMessages) {
                    MathJax.Message.Set(c.concat(a), 0)
                }
            },
            processError: function(b, c, a) {
                if (!b.restart) {
                    if (!this.config.errorSettings.message) {
                        throw b
                    }
                    this.formatError(c.scripts[c.i], b);
                    c.i++
                }
                this.processMessage(c, a);
                return MathJax.Callback.After(["process" + a, this, c], b.restart)
            },
            formatError: function(b, e) {
                var d = "Error: " + e.message + "\n";
                if (e.sourceURL) {
                    d += "\nfile: " + e.sourceURL
                }
                if (e.line) {
                    d += "\nline: " + e.line
                }
                b.MathJax.error = MathJax.OutputJax.Error.Jax(d, b);
                var f = this.config.errorSettings;
                var a = MathJax.Localization._(f.messageId, f.message);
                var c = MathJax.HTML.Element("span", {
                    className: "MathJax_Error",
                    jaxID: "Error",
                    isMathJax: true
                }, a);
                if (MathJax.Extension.MathEvents) {
                    c.oncontextmenu = MathJax.Extension.MathEvents.Event.Menu;
                    c.onmousedown = MathJax.Extension.MathEvents.Event.Mousedown
                } else {
                    MathJax.Ajax.Require("[MathJax]/extensions/MathEvents.js", function() {
                        c.oncontextmenu = MathJax.Extension.MathEvents.Event.Menu;
                        c.onmousedown = MathJax.Extension.MathEvents.Event.Mousedown
                    })
                }
                b.parentNode.insertBefore(c, b);
                if (b.MathJax.preview) {
                    b.MathJax.preview.innerHTML = ""
                }
                this.lastError = e;
                this.signal.Post(["Math Processing Error", b, e])
            },
            RestartAfter: function(a) {
                throw this.Insert(Error("restart"), {
                    restart: MathJax.Callback(a)
                })
            },
            elementCallback: function(c, f) {
                if (f == null && (c instanceof Array || typeof c === "function")) {
                    try {
                        MathJax.Callback(c);
                        f = c;
                        c = null
                    } catch (d) {}
                }
                if (c == null) {
                    c = this.config.elements || []
                }
                if (!(c instanceof Array)) {
                    c = [c]
                }
                c = [].concat(c);
                for (var b = 0, a = c.length; b < a; b++) {
                    if (typeof(c[b]) === "string") {
                        c[b] = document.getElementById(c[b])
                    }
                }
                if (!document.body) {
                    document.body = document.getElementsByTagName("body")[0]
                }
                if (c.length == 0) {
                    c.push(document.body)
                }
                if (!f) {
                    f = {}
                }
                return {
                    elements: c,
                    callback: f
                }
            },
            elementScripts: function(a) {
                if (typeof(a) === "string") {
                    a = document.getElementById(a)
                }
                if (!document.body) {
                    document.body = document.getElementsByTagName("body")[0]
                }
                if (a == null) {
                    a = document.body
                }
                if (a.tagName != null && a.tagName.toLowerCase() === "script") {
                    return [a]
                }
                return a.getElementsByTagName("script")
            },
            Insert: function(c, a) {
                for (var b in a) {
                    if (a.hasOwnProperty(b)) {
                        if (typeof a[b] === "object" && !(a[b] instanceof Array) && (typeof c[b] === "object" || typeof c[b] === "function")) {
                            this.Insert(c[b], a[b])
                        } else {
                            c[b] = a[b]
                        }
                    }
                }
                return c
            },
            SplitList: ("trim" in String.prototype ? function(a) {
                return a.trim().split(/\s+/)
            } : function(a) {
                return a.replace(/^\s+/, "").replace(/\s+$/, "").split(/\s+/)
            })
        };
        MathJax.Hub.Insert(MathJax.Hub.config.styles, MathJax.Message.styles);
        MathJax.Hub.Insert(MathJax.Hub.config.styles, {
            ".MathJax_Error": MathJax.Hub.config.errorSettings.style
        });
        MathJax.Extension = {};
        MathJax.Hub.Configured = MathJax.Callback({});
        MathJax.Hub.Startup = {
            script: "",
            queue: MathJax.Callback.Queue(),
            signal: MathJax.Callback.Signal("Startup"),
            params: {},
            Config: function() {
                this.queue.Push(["Post", this.signal, "Begin Config"]);
                if (this.params.locale) {
                    MathJax.Localization.locale = this.params.locale;
                    MathJax.Hub.config.menuSettings.locale = this.params.locale
                }
                var b = MathJax.HTML.Cookie.Get("user");
                if (b.URL || b.Config) {
                    if (confirm(MathJax.Localization._("CookieConfig", "MathJax has found a user-configuration cookie that includes code to be run. Do you want to run it?\n\n(You should press Cancel unless you set up the cookie yourself.)"))) {
                        if (b.URL) {
                            this.queue.Push(["Require", MathJax.Ajax, b.URL])
                        }
                        if (b.Config) {
                            this.queue.Push(new Function(b.Config))
                        }
                    } else {
                        MathJax.HTML.Cookie.Set("user", {})
                    }
                }
                if (this.params.config) {
                    var d = this.params.config.split(/,/);
                    for (var c = 0, a = d.length; c < a; c++) {
                        if (!d[c].match(/\.js$/)) {
                            d[c] += ".js"
                        }
                        this.queue.Push(["Require", MathJax.Ajax, this.URL("config", d[c])])
                    }
                }
                if (this.script.match(/\S/)) {
                    this.queue.Push(this.script + ";\n1;")
                }
                this.queue.Push(["ConfigDelay", this], ["ConfigBlocks", this], [function(e) {
                    return e.loadArray(MathJax.Hub.config.config, "config", null, true)
                }, this], ["Post", this.signal, "End Config"])
            },
            ConfigDelay: function() {
                var a = this.params.delayStartupUntil || MathJax.Hub.config.delayStartupUntil;
                if (a === "onload") {
                    return this.onload
                }
                if (a === "configured") {
                    return MathJax.Hub.Configured
                }
                return a
            },
            ConfigBlocks: function() {
                var c = document.getElementsByTagName("script");
                var f = null,
                    b = MathJax.Callback.Queue();
                for (var d = 0, a = c.length; d < a; d++) {
                    var e = String(c[d].type).replace(/ /g, "");
                    if (e.match(/^text\/x-mathjax-config(;.*)?$/) && !e.match(/;executed=true/)) {
                        c[d].type += ";executed=true";
                        f = b.Push(c[d].innerHTML + ";\n1;")
                    }
                }
                return f
            },
            Cookie: function() {
                return this.queue.Push(["Post", this.signal, "Begin Cookie"], ["Get", MathJax.HTML.Cookie, "menu", MathJax.Hub.config.menuSettings], [function(d) {
                    if (d.menuSettings.locale) {
                        MathJax.Localization.locale = d.menuSettings.locale
                    }
                    var f = d.menuSettings.renderer,
                        b = d.jax;
                    if (f) {
                        var c = "output/" + f;
                        b.sort();
                        for (var e = 0, a = b.length; e < a; e++) {
                            if (b[e].substr(0, 7) === "output/") {
                                break
                            }
                        }
                        if (e == a - 1) {
                            b.pop()
                        } else {
                            while (e < a) {
                                if (b[e] === c) {
                                    b.splice(e, 1);
                                    break
                                }
                                e++
                            }
                        }
                        b.unshift(c)
                    }
                }, MathJax.Hub.config], ["Post", this.signal, "End Cookie"])
            },
            Styles: function() {
                return this.queue.Push(["Post", this.signal, "Begin Styles"], ["loadArray", this, MathJax.Hub.config.styleSheets, "config"], ["Styles", MathJax.Ajax, MathJax.Hub.config.styles], ["Post", this.signal, "End Styles"])
            },
            Jax: function() {
                var f = MathJax.Hub.config,
                    c = MathJax.Hub.outputJax;
                for (var g = 0, b = f.jax.length, d = 0; g < b; g++) {
                    var e = f.jax[g].substr(7);
                    if (f.jax[g].substr(0, 7) === "output/" && c.order[e] == null) {
                        c.order[e] = d;
                        d++
                    }
                }
                var a = MathJax.Callback.Queue();
                return a.Push(["Post", this.signal, "Begin Jax"], ["loadArray", this, f.jax, "jax", "config.js"], ["Post", this.signal, "End Jax"])
            },
            Extensions: function() {
                var a = MathJax.Callback.Queue();
                return a.Push(["Post", this.signal, "Begin Extensions"], ["loadArray", this, MathJax.Hub.config.extensions, "extensions"], ["Post", this.signal, "End Extensions"])
            },
            Message: function() {
                MathJax.Message.Init(true)
            },
            Menu: function() {
                var b = MathJax.Hub.config.menuSettings,
                    a = MathJax.Hub.outputJax,
                    d;
                for (var c in a) {
                    if (a.hasOwnProperty(c)) {
                        if (a[c].length) {
                            d = a[c];
                            break
                        }
                    }
                }
                if (d && d.length) {
                    if (b.renderer && b.renderer !== d[0].id) {
                        d.unshift(MathJax.OutputJax[b.renderer])
                    }
                    b.renderer = d[0].id
                }
            },
            Hash: function() {
                if (MathJax.Hub.config.positionToHash && document.location.hash && document.body && document.body.scrollIntoView) {
                    var d = document.location.hash.substr(1);
                    var f = document.getElementById(d);
                    if (!f) {
                        var c = document.getElementsByTagName("a");
                        for (var e = 0, b = c.length; e < b; e++) {
                            if (c[e].name === d) {
                                f = c[e];
                                break
                            }
                        }
                    }
                    if (f) {
                        while (!f.scrollIntoView) {
                            f = f.parentNode
                        }
                        f = this.HashCheck(f);
                        if (f && f.scrollIntoView) {
                            setTimeout(function() {
                                f.scrollIntoView(true)
                            }, 1)
                        }
                    }
                }
            },
            HashCheck: function(b) {
                if (b.isMathJax) {
                    var a = MathJax.Hub.getJaxFor(b);
                    if (a && MathJax.OutputJax[a.outputJax].hashCheck) {
                        b = MathJax.OutputJax[a.outputJax].hashCheck(b)
                    }
                }
                return b
            },
            MenuZoom: function() {
                if (!MathJax.Extension.MathMenu) {
                    setTimeout(function() {
                        MathJax.Callback.Queue(["Require", MathJax.Ajax, "[MathJax]/extensions/MathMenu.js", {}], ["loadDomain", MathJax.Localization, "MathMenu"])
                    }, 1000)
                } else {
                    setTimeout(MathJax.Callback(["loadDomain", MathJax.Localization, "MathMenu"]), 1000)
                }
                if (!MathJax.Extension.MathZoom) {
                    setTimeout(MathJax.Callback(["Require", MathJax.Ajax, "[MathJax]/extensions/MathZoom.js", {}]), 2000)
                }
            },
            onLoad: function() {
                var a = this.onload = MathJax.Callback(function() {
                    MathJax.Hub.Startup.signal.Post("onLoad")
                });
                if (document.body && document.readyState) {
                    if (MathJax.Hub.Browser.isMSIE) {
                        if (document.readyState === "complete") {
                            return [a]
                        }
                    } else {
                        if (document.readyState !== "loading") {
                            return [a]
                        }
                    }
                }
                if (window.addEventListener) {
                    window.addEventListener("load", a, false);
                    if (!this.params.noDOMContentEvent) {
                        window.addEventListener("DOMContentLoaded", a, false)
                    }
                } else {
                    if (window.attachEvent) {
                        window.attachEvent("onload", a)
                    } else {
                        window.onload = a
                    }
                }
                return a
            },
            Typeset: function(a, b) {
                if (MathJax.Hub.config.skipStartupTypeset) {
                    return function() {}
                }
                return this.queue.Push(["Post", this.signal, "Begin Typeset"], ["Typeset", MathJax.Hub, a, b], ["Post", this.signal, "End Typeset"])
            },
            URL: function(b, a) {
                if (!a.match(/^([a-z]+:\/\/|\[|\/)/)) {
                    a = "[MathJax]/" + b + "/" + a
                }
                return a
            },
            loadArray: function(b, f, c, a) {
                if (b) {
                    if (!(b instanceof Array)) {
                        b = [b]
                    }
                    if (b.length) {
                        var h = MathJax.Callback.Queue(),
                            j = {},
                            e;
                        for (var g = 0, d = b.length; g < d; g++) {
                            e = this.URL(f, b[g]);
                            if (c) {
                                e += "/" + c
                            }
                            if (a) {
                                h.Push(["Require", MathJax.Ajax, e, j])
                            } else {
                                h.Push(MathJax.Ajax.Require(e, j))
                            }
                        }
                        return h.Push({})
                    }
                }
                return null
            }
        };
        (function(d) {
            var b = window[d],
                e = "[" + d + "]";
            var c = b.Hub,
                a = b.Ajax,
                f = b.Callback;
            var g = MathJax.Object.Subclass({
                JAXFILE: "jax.js",
                require: null,
                config: {},
                Init: function(i, h) {
                    if (arguments.length === 0) {
                        return this
                    }
                    return (this.constructor.Subclass(i, h))()
                },
                Augment: function(k, j) {
                    var i = this.constructor,
                        h = {};
                    if (k != null) {
                        for (var l in k) {
                            if (k.hasOwnProperty(l)) {
                                if (typeof k[l] === "function") {
                                    i.protoFunction(l, k[l])
                                } else {
                                    h[l] = k[l]
                                }
                            }
                        }
                        if (k.toString !== i.prototype.toString && k.toString !== {}.toString) {
                            i.protoFunction("toString", k.toString)
                        }
                    }
                    c.Insert(i.prototype, h);
                    i.Augment(null, j);
                    return this
                },
                Translate: function(h, i) {
                    throw Error(this.directory + "/" + this.JAXFILE + " failed to define the Translate() method")
                },
                Register: function(h) {},
                Config: function() {
                    this.config = c.CombineConfig(this.id, this.config);
                    if (this.config.Augment) {
                        this.Augment(this.config.Augment)
                    }
                },
                Startup: function() {},
                loadComplete: function(i) {
                    if (i === "config.js") {
                        return a.loadComplete(this.directory + "/" + i)
                    } else {
                        var h = f.Queue();
                        h.Push(c.Register.StartupHook("End Config", {}), ["Post", c.Startup.signal, this.id + " Jax Config"], ["Config", this], ["Post", c.Startup.signal, this.id + " Jax Require"], [function(j) {
                            return MathJax.Hub.Startup.loadArray(j.require, this.directory)
                        }, this], [function(j, k) {
                            return MathJax.Hub.Startup.loadArray(j.extensions, "extensions/" + k)
                        }, this.config || {}, this.id], ["Post", c.Startup.signal, this.id + " Jax Startup"], ["Startup", this], ["Post", c.Startup.signal, this.id + " Jax Ready"]);
                        if (this.copyTranslate) {
                            h.Push([function(j) {
                                j.preProcess = j.preTranslate;
                                j.Process = j.Translate;
                                j.postProcess = j.postTranslate
                            }, this.constructor.prototype])
                        }
                        return h.Push(["loadComplete", a, this.directory + "/" + i])
                    }
                }
            }, {
                id: "Jax",
                version: "2.2",
                directory: e + "/jax",
                extensionDir: e + "/extensions"
            });
            b.InputJax = g.Subclass({
                elementJax: "mml",
                sourceMenuTitle: ["OriginalForm", "Original Form"],
                copyTranslate: true,
                Process: function(l, q) {
                    var j = f.Queue(),
                        o;
                    var k = this.elementJax;
                    if (!(k instanceof Array)) {
                        k = [k]
                    }
                    for (var n = 0, h = k.length; n < h; n++) {
                        o = b.ElementJax.directory + "/" + k[n] + "/" + this.JAXFILE;
                        if (!this.require) {
                            this.require = []
                        } else {
                            if (!(this.require instanceof Array)) {
                                this.require = [this.require]
                            }
                        }
                        this.require.push(o);
                        j.Push(a.Require(o))
                    }
                    o = this.directory + "/" + this.JAXFILE;
                    var p = j.Push(a.Require(o));
                    if (!p.called) {
                        this.constructor.prototype.Process = function() {
                            if (!p.called) {
                                return p
                            }
                            throw Error(o + " failed to load properly")
                        }
                    }
                    k = c.outputJax["jax/" + k[0]];
                    if (k) {
                        j.Push(a.Require(k[0].directory + "/" + this.JAXFILE))
                    }
                    return j.Push({})
                },
                needsUpdate: function(h) {
                    var i = h.SourceElement();
                    return (h.originalText !== b.HTML.getScript(i))
                },
                Register: function(h) {
                    if (!c.inputJax) {
                        c.inputJax = {}
                    }
                    c.inputJax[h] = this
                }
            }, {
                id: "InputJax",
                version: "2.2",
                directory: g.directory + "/input",
                extensionDir: g.extensionDir
            });
            b.OutputJax = g.Subclass({
                copyTranslate: true,
                preProcess: function(j) {
                    var i, h = this.directory + "/" + this.JAXFILE;
                    this.constructor.prototype.preProcess = function(k) {
                        if (!i.called) {
                            return i
                        }
                        throw Error(h + " failed to load properly")
                    };
                    i = a.Require(h);
                    return i
                },
                Register: function(i) {
                    var h = c.outputJax;
                    if (!h[i]) {
                        h[i] = []
                    }
                    if (h[i].length && (this.id === c.config.menuSettings.renderer || (h.order[this.id] || 0) < (h.order[h[i][0].id] || 0))) {
                        h[i].unshift(this)
                    } else {
                        h[i].push(this)
                    }
                    if (!this.require) {
                        this.require = []
                    } else {
                        if (!(this.require instanceof Array)) {
                            this.require = [this.require]
                        }
                    }
                    this.require.push(b.ElementJax.directory + "/" + (i.split(/\//)[1]) + "/" + this.JAXFILE)
                },
                Remove: function(h) {}
            }, {
                id: "OutputJax",
                version: "2.2",
                directory: g.directory + "/output",
                extensionDir: g.extensionDir,
                fontDir: e + (b.isPacked ? "" : "/..") + "/fonts",
                imageDir: e + (b.isPacked ? "" : "/..") + "/images"
            });
            b.ElementJax = g.Subclass({
                Init: function(i, h) {
                    return this.constructor.Subclass(i, h)
                },
                inputJax: null,
                outputJax: null,
                inputID: null,
                originalText: "",
                mimeType: "",
                sourceMenuTitle: ["MathMLcode", "MathML Code"],
                Text: function(i, j) {
                    var h = this.SourceElement();
                    b.HTML.setScript(h, i);
                    h.MathJax.state = this.STATE.UPDATE;
                    return c.Update(h, j)
                },
                Reprocess: function(i) {
                    var h = this.SourceElement();
                    h.MathJax.state = this.STATE.UPDATE;
                    return c.Reprocess(h, i)
                },
                Update: function(h) {
                    return this.Rerender(h)
                },
                Rerender: function(i) {
                    var h = this.SourceElement();
                    h.MathJax.state = this.STATE.OUTPUT;
                    return c.Process(h, i)
                },
                Remove: function(h) {
                    if (this.hover) {
                        this.hover.clear(this)
                    }
                    b.OutputJax[this.outputJax].Remove(this);
                    if (!h) {
                        c.signal.Post(["Remove Math", this.inputID]);
                        this.Detach()
                    }
                },
                needsUpdate: function() {
                    return b.InputJax[this.inputJax].needsUpdate(this)
                },
                SourceElement: function() {
                    return document.getElementById(this.inputID)
                },
                Attach: function(i, j) {
                    var h = i.MathJax.elementJax;
                    if (i.MathJax.state === this.STATE.UPDATE) {
                        h.Clone(this)
                    } else {
                        h = i.MathJax.elementJax = this;
                        if (i.id) {
                            this.inputID = i.id
                        } else {
                            i.id = this.inputID = b.ElementJax.GetID();
                            this.newID = 1
                        }
                    }
                    h.originalText = b.HTML.getScript(i);
                    h.inputJax = j;
                    if (h.root) {
                        h.root.inputID = h.inputID
                    }
                    return h
                },
                Detach: function() {
                    var h = this.SourceElement();
                    if (!h) {
                        return
                    }
                    try {
                        delete h.MathJax
                    } catch (i) {
                        h.MathJax = null
                    }
                    if (this.newID) {
                        h.id = ""
                    }
                },
                Clone: function(h) {
                    var i;
                    for (i in this) {
                        if (!this.hasOwnProperty(i)) {
                            continue
                        }
                        if (typeof(h[i]) === "undefined" && i !== "newID") {
                            delete this[i]
                        }
                    }
                    for (i in h) {
                        if (!h.hasOwnProperty(i)) {
                            continue
                        }
                        if (typeof(this[i]) === "undefined" || (this[i] !== h[i] && i !== "inputID")) {
                            this[i] = h[i]
                        }
                    }
                }
            }, {
                id: "ElementJax",
                version: "2.2",
                directory: g.directory + "/element",
                extensionDir: g.extensionDir,
                ID: 0,
                STATE: {
                    PENDING: 1,
                    PROCESSED: 2,
                    UPDATE: 3,
                    OUTPUT: 4
                },
                GetID: function() {
                    this.ID++;
                    return "MathJax-Element-" + this.ID
                },
                Subclass: function() {
                    var h = g.Subclass.apply(this, arguments);
                    h.loadComplete = this.prototype.loadComplete;
                    return h
                }
            });
            b.ElementJax.prototype.STATE = b.ElementJax.STATE;
            b.OutputJax.Error = {
                id: "Error",
                version: "2.2",
                config: {},
                ContextMenu: function() {
                    return b.Extension.MathEvents.Event.ContextMenu.apply(b.Extension.MathEvents.Event, arguments)
                },
                Mousedown: function() {
                    return b.Extension.MathEvents.Event.AltContextMenu.apply(b.Extension.MathEvents.Event, arguments)
                },
                getJaxFromMath: function(h) {
                    return (h.nextSibling.MathJax || {}).error
                },
                Jax: function(j, i) {
                    var h = MathJax.Hub.inputJax[i.type.replace(/ *;(.|\s)*/, "")];
                    return {
                        inputJax: (h || {
                            id: "Error"
                        }).id,
                        outputJax: "Error",
                        sourceMenuTitle: ["ErrorMessage", "Error Message"],
                        sourceMenuFormat: "Error",
                        originalText: MathJax.HTML.getScript(i),
                        errorText: j
                    }
                }
            };
            b.InputJax.Error = {
                id: "Error",
                version: "2.2",
                config: {},
                sourceMenuTitle: ["OriginalForm", "Original Form"]
            }
        })("MathJax");
        (function(l) {
            var f = window[l];
            if (!f) {
                f = window[l] = {}
            }
            var c = f.Hub;
            var q = c.Startup;
            var u = c.config;
            var e = document.getElementsByTagName("head")[0];
            if (!e) {
                e = document.childNodes[0]
            }
            var b = (document.documentElement || document).getElementsByTagName("script");
            var d = new RegExp("(^|/)" + l + "\\.js(\\?.*)?$");
            for (var o = b.length - 1; o >= 0; o--) {
                if ((b[o].src || "").match(d)) {
                    q.script = b[o].innerHTML;
                    if (RegExp.$2) {
                        var r = RegExp.$2.substr(1).split(/\&/);
                        for (var n = 0, h = r.length; n < h; n++) {
                            var k = r[n].match(/(.*)=(.*)/);
                            if (k) {
                                q.params[unescape(k[1])] = unescape(k[2])
                            }
                        }
                    }
                    u.root = b[o].src.replace(/(^|\/)[^\/]*(\?.*)?$/, "");
                    break
                }
            }
            f.Ajax.config = u;
            var a = {
                isMac: (navigator.platform.substr(0, 3) === "Mac"),
                isPC: (navigator.platform.substr(0, 3) === "Win"),
                isMSIE: (window.ActiveXObject != null && window.clipboardData != null),
                isFirefox: (navigator.userAgent.match(/Gecko/) != null && navigator.userAgent.match(/KHTML/) == null),
                isSafari: (navigator.userAgent.match(/ (Apple)?WebKit\//) != null && (!window.chrome || window.chrome.loadTimes == null)),
                isChrome: (window.chrome != null && window.chrome.loadTimes != null),
                isOpera: (window.opera != null && window.opera.version != null),
                isKonqueror: (window.hasOwnProperty && window.hasOwnProperty("konqueror") && navigator.vendor == "KDE"),
                versionAtLeast: function(x) {
                    var w = (this.version).split(".");
                    x = (new String(x)).split(".");
                    for (var y = 0, j = x.length; y < j; y++) {
                        if (w[y] != x[y]) {
                            return parseInt(w[y] || "0") >= parseInt(x[y])
                        }
                    }
                    return true
                },
                Select: function(j) {
                    var i = j[c.Browser];
                    if (i) {
                        return i(c.Browser)
                    }
                    return null
                }
            };
            var g = navigator.userAgent.replace(/^Mozilla\/(\d+\.)+\d+ /, "").replace(/[a-z][-a-z0-9._: ]+\/\d+[^ ]*-[^ ]*\.([a-z][a-z])?\d+ /i, "").replace(/Gentoo |Ubuntu\/(\d+\.)*\d+ (\([^)]*\) )?/, "");
            c.Browser = c.Insert(c.Insert(new String("Unknown"), {
                version: "0.0"
            }), a);
            for (var t in a) {
                if (a.hasOwnProperty(t)) {
                    if (a[t] && t.substr(0, 2) === "is") {
                        t = t.slice(2);
                        if (t === "Mac" || t === "PC") {
                            continue
                        }
                        c.Browser = c.Insert(new String(t), a);
                        var p = new RegExp(".*(Version)/((?:\\d+\\.)+\\d+)|.*(" + t + ")" + (t == "MSIE" ? " " : "/") + "((?:\\d+\\.)*\\d+)|(?:^|\\(| )([a-z][-a-z0-9._: ]+|(?:Apple)?WebKit)/((?:\\d+\\.)+\\d+)");
                        var s = p.exec(g) || ["", "", "", "unknown", "0.0"];
                        c.Browser.name = (s[1] == "Version" ? t : (s[3] || s[5]));
                        c.Browser.version = s[2] || s[4] || s[6];
                        break
                    }
                }
            }
            c.Browser.Select({
                Safari: function(j) {
                    var i = parseInt((String(j.version).split("."))[0]);
                    if (i > 85) {
                        j.webkit = j.version
                    }
                    if (i >= 534) {
                        j.version = "5.1"
                    } else {
                        if (i >= 533) {
                            j.version = "5.0"
                        } else {
                            if (i >= 526) {
                                j.version = "4.0"
                            } else {
                                if (i >= 525) {
                                    j.version = "3.1"
                                } else {
                                    if (i > 500) {
                                        j.version = "3.0"
                                    } else {
                                        if (i > 400) {
                                            j.version = "2.0"
                                        } else {
                                            if (i > 85) {
                                                j.version = "1.0"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    j.isMobile = (navigator.appVersion.match(/Mobile/i) != null);
                    j.noContextMenu = j.isMobile
                },
                Firefox: function(j) {
                    if ((j.version === "0.0" || navigator.userAgent.match(/Firefox/) == null) && navigator.product === "Gecko") {
                        var m = navigator.userAgent.match(/[\/ ]rv:(\d+\.\d.*?)[\) ]/);
                        if (m) {
                            j.version = m[1]
                        } else {
                            var i = (navigator.buildID || navigator.productSub || "0").substr(0, 8);
                            if (i >= "20111220") {
                                j.version = "9.0"
                            } else {
                                if (i >= "20111120") {
                                    j.version = "8.0"
                                } else {
                                    if (i >= "20110927") {
                                        j.version = "7.0"
                                    } else {
                                        if (i >= "20110816") {
                                            j.version = "6.0"
                                        } else {
                                            if (i >= "20110621") {
                                                j.version = "5.0"
                                            } else {
                                                if (i >= "20110320") {
                                                    j.version = "4.0"
                                                } else {
                                                    if (i >= "20100121") {
                                                        j.version = "3.6"
                                                    } else {
                                                        if (i >= "20090630") {
                                                            j.version = "3.5"
                                                        } else {
                                                            if (i >= "20080617") {
                                                                j.version = "3.0"
                                                            } else {
                                                                if (i >= "20061024") {
                                                                    j.version = "2.0"
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    j.isMobile = (navigator.appVersion.match(/Android/i) != null || navigator.userAgent.match(/ Fennec\//) != null || navigator.userAgent.match(/Mobile/) != null)
                },
                Opera: function(i) {
                    i.version = opera.version()
                },
                MSIE: function(j) {
                    j.isIE9 = !!(document.documentMode && (window.performance || window.msPerformance));
                    MathJax.HTML.setScriptBug = !j.isIE9 || document.documentMode < 9;
                    var v = false;
                    try {
                        new ActiveXObject("MathPlayer.Factory.1");
                        j.hasMathPlayer = v = true
                    } catch (m) {}
                    try {
                        if (v && !q.params.NoMathPlayer) {
                            var i = document.createElement("object");
                            i.id = "mathplayer";
                            i.classid = "clsid:32F66A20-7614-11D4-BD11-00104BD3F987";
                            document.getElementsByTagName("head")[0].appendChild(i);
                            document.namespaces.add("m", "http://www.w3.org/1998/Math/MathML");
                            j.mpNamespace = true;
                            if (document.readyState && (document.readyState === "loading" || document.readyState === "interactive")) {
                                document.write('<?import namespace="m" implementation="#MathPlayer">');
                                j.mpImported = true
                            }
                        } else {
                            document.namespaces.add("mjx_IE_fix", "http://www.w3.org/1999/xlink")
                        }
                    } catch (m) {}
                }
            });
            c.Browser.Select(MathJax.Message.browsers);
            c.queue = f.Callback.Queue();
            c.queue.Push(["Post", q.signal, "Begin"], ["Config", q], ["Cookie", q], ["Styles", q], ["Message", q], function() {
                var i = f.Callback.Queue(q.Jax(), q.Extensions());
                return i.Push({})
            }, ["Menu", q], q.onLoad(), function() {
                MathJax.isReady = true
            }, ["Typeset", q], ["Hash", q], ["MenuZoom", q], ["Post", q.signal, "End"])
        })("MathJax")
    }
};