package com.movilizer.util.mel;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.movilizer.util.mel.MelHelper.toMelWriteTextLines;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MelHelperTest {

    String html = "<html>\n" +
            "<head>\n" +
            "    <script>\n" +
            "    var Chart = function (s) {\n" +
            "        function v(a, c, b) {\n" +
            "            a = A((a - c.graphMin) / (c.steps * c.stepValue), 1, 0);\n" +
            "            return b * c.steps * a\n" +
            "        }\n" +
            "\n" +
            "        function x(a, c, b, e) {\n" +
            "            function h() {\n" +
            "                g += f;\n" +
            "                var k = a.animation ? A(d(g), null, 0) : 1;\n" +
            "                e.clearRect(0, 0, q, u);\n" +
            "                a.scaleOverlay ? (b(k), c()) : (c(), b(k));\n" +
            "                if (1 >= g)D(h); else if (\"function\" == typeof a.onAnimationComplete)a.onAnimationComplete()\n" +
            "            }\n" +
            "\n" +
            "            var f = a.animation ? 1 / A(a.animationSteps, Number.MAX_VALUE, 1) : 1, d = B[a.animationEasing], g = a.animation ? 0 : 1;\n" +
            "            \"function\" !== typeof c && (c = function () {\n" +
            "            });\n" +
            "            D(h)\n" +
            "        }\n" +
            "\n" +
            "        function C(a, c, b, e, h, f) {\n" +
            "            var d;\n" +
            "            a =\n" +
            "                    Math.floor(Math.log(e - h) / Math.LN10);\n" +
            "            h = Math.floor(h / (1 * Math.pow(10, a))) * Math.pow(10, a);\n" +
            "            e = Math.ceil(e / (1 * Math.pow(10, a))) * Math.pow(10, a) - h;\n" +
            "            a = Math.pow(10, a);\n" +
            "            for (d = Math.round(e / a); d < b || d > c;)a = d < b ? a / 2 : 2 * a, d = Math.round(e / a);\n" +
            "            c = [];\n" +
            "            z(f, c, d, h, a);\n" +
            "            return{steps: d, stepValue: a, graphMin: h, labels: c}\n" +
            "        }\n" +
            "\n" +
            "        function z(a, c, b, e, h) {\n" +
            "            if (a)for (var f = 1; f < b + 1; f++)c.push(E(a, {value: (e + h * f).toFixed(0 != h % 1 ? h.toString().split(\".\")[1].length : 0)}))\n" +
            "        }\n" +
            "\n" +
            "        function A(a, c, b) {\n" +
            "            return!isNaN(parseFloat(c)) && isFinite(c) && a > c ? c : !isNaN(parseFloat(b)) &&\n" +
            "                    isFinite(b) && a < b ? b : a\n" +
            "        }\n" +
            "\n" +
            "        function y(a, c) {\n" +
            "            var b = {}, e;\n" +
            "            for (e in a)b[e] = a[e];\n" +
            "            for (e in c)b[e] = c[e];\n" +
            "            return b\n" +
            "        }\n" +
            "\n" +
            "        function E(a, c) {\n" +
            "            var b = !/\\W/.test(a) ? F[a] = F[a] || E(document.getElementById(a).innerHTML) : new Function(\"obj\", \"var p=[],print=function(){p.push.apply(p,arguments);};with(obj){p.push(\\'\" + a.replace(/[\\r\\t\\n]/g, \" \").split(\"<%\").join(\"\\t\").replace(/((^|%>)[^\\t]*)\\'/g, \"$1\\r\").replace(/\\t=(.*?)%>/g, \"\\',$1,\\'\").split(\"\\t\").join(\"\\');\").split(\"%>\").join(\"p.push(\\'\").split(\"\\r\").join(\"\\\\\\'\") + \"\\');}return p.join(\\'\\');\");\n" +
            "            return c ?\n" +
            "                    b(c) : b\n" +
            "        }\n" +
            "\n" +
            "        var r = this, B = {linear: function (a) {\n" +
            "            return a\n" +
            "        }, easeInQuad: function (a) {\n" +
            "            return a * a\n" +
            "        }, easeOutQuad: function (a) {\n" +
            "            return-1 * a * (a - 2)\n" +
            "        }, easeInOutQuad: function (a) {\n" +
            "            return 1 > (a /= 0.5) ? 0.5 * a * a : -0.5 * (--a * (a - 2) - 1)\n" +
            "        }, easeInCubic: function (a) {\n" +
            "            return a * a * a\n" +
            "        }, easeOutCubic: function (a) {\n" +
            "            return 1 * ((a = a / 1 - 1) * a * a + 1)\n" +
            "        }, easeInOutCubic: function (a) {\n" +
            "            return 1 > (a /= 0.5) ? 0.5 * a * a * a : 0.5 * ((a -= 2) * a * a + 2)\n" +
            "        }, easeInQuart: function (a) {\n" +
            "            return a * a * a * a\n" +
            "        }, easeOutQuart: function (a) {\n" +
            "            return-1 * ((a = a / 1 - 1) * a * a * a - 1)\n" +
            "        }, easeInOutQuart: function (a) {\n" +
            "            return 1 > (a /= 0.5) ?\n" +
            "                    0.5 * a * a * a * a : -0.5 * ((a -= 2) * a * a * a - 2)\n" +
            "        }, easeInQuint: function (a) {\n" +
            "            return 1 * (a /= 1) * a * a * a * a\n" +
            "        }, easeOutQuint: function (a) {\n" +
            "            return 1 * ((a = a / 1 - 1) * a * a * a * a + 1)\n" +
            "        }, easeInOutQuint: function (a) {\n" +
            "            return 1 > (a /= 0.5) ? 0.5 * a * a * a * a * a : 0.5 * ((a -= 2) * a * a * a * a + 2)\n" +
            "        }, easeInSine: function (a) {\n" +
            "            return-1 * Math.cos(a / 1 * (Math.PI / 2)) + 1\n" +
            "        }, easeOutSine: function (a) {\n" +
            "            return 1 * Math.sin(a / 1 * (Math.PI / 2))\n" +
            "        }, easeInOutSine: function (a) {\n" +
            "            return-0.5 * (Math.cos(Math.PI * a / 1) - 1)\n" +
            "        }, easeInExpo: function (a) {\n" +
            "            return 0 == a ? 1 : 1 * Math.pow(2, 10 * (a / 1 - 1))\n" +
            "        }, easeOutExpo: function (a) {\n" +
            "            return 1 ==\n" +
            "                    a ? 1 : 1 * (-Math.pow(2, -10 * a / 1) + 1)\n" +
            "        }, easeInOutExpo: function (a) {\n" +
            "            return 0 == a ? 0 : 1 == a ? 1 : 1 > (a /= 0.5) ? 0.5 * Math.pow(2, 10 * (a - 1)) : 0.5 * (-Math.pow(2, -10 * --a) + 2)\n" +
            "        }, easeInCirc: function (a) {\n" +
            "            return 1 <= a ? a : -1 * (Math.sqrt(1 - (a /= 1) * a) - 1)\n" +
            "        }, easeOutCirc: function (a) {\n" +
            "            return 1 * Math.sqrt(1 - (a = a / 1 - 1) * a)\n" +
            "        }, easeInOutCirc: function (a) {\n" +
            "            return 1 > (a /= 0.5) ? -0.5 * (Math.sqrt(1 - a * a) - 1) : 0.5 * (Math.sqrt(1 - (a -= 2) * a) + 1)\n" +
            "        }, easeInElastic: function (a) {\n" +
            "            var c = 1.70158, b = 0, e = 1;\n" +
            "            if (0 == a)return 0;\n" +
            "            if (1 == (a /= 1))return 1;\n" +
            "            b || (b = 0.3);\n" +
            "            e < Math.abs(1) ? (e = 1, c = b / 4) : c = b / (2 *\n" +
            "                    Math.PI) * Math.asin(1 / e);\n" +
            "            return-(e * Math.pow(2, 10 * (a -= 1)) * Math.sin((1 * a - c) * 2 * Math.PI / b))\n" +
            "        }, easeOutElastic: function (a) {\n" +
            "            var c = 1.70158, b = 0, e = 1;\n" +
            "            if (0 == a)return 0;\n" +
            "            if (1 == (a /= 1))return 1;\n" +
            "            b || (b = 0.3);\n" +
            "            e < Math.abs(1) ? (e = 1, c = b / 4) : c = b / (2 * Math.PI) * Math.asin(1 / e);\n" +
            "            return e * Math.pow(2, -10 * a) * Math.sin((1 * a - c) * 2 * Math.PI / b) + 1\n" +
            "        }, easeInOutElastic: function (a) {\n" +
            "            var c = 1.70158, b = 0, e = 1;\n" +
            "            if (0 == a)return 0;\n" +
            "            if (2 == (a /= 0.5))return 1;\n" +
            "            b || (b = 1 * 0.3 * 1.5);\n" +
            "            e < Math.abs(1) ? (e = 1, c = b / 4) : c = b / (2 * Math.PI) * Math.asin(1 / e);\n" +
            "            return 1 > a ? -0.5 * e * Math.pow(2, 10 *\n" +
            "                    (a -= 1)) * Math.sin((1 * a - c) * 2 * Math.PI / b) : 0.5 * e * Math.pow(2, -10 * (a -= 1)) * Math.sin((1 * a - c) * 2 * Math.PI / b) + 1\n" +
            "        }, easeInBack: function (a) {\n" +
            "            return 1 * (a /= 1) * a * (2.70158 * a - 1.70158)\n" +
            "        }, easeOutBack: function (a) {\n" +
            "            return 1 * ((a = a / 1 - 1) * a * (2.70158 * a + 1.70158) + 1)\n" +
            "        }, easeInOutBack: function (a) {\n" +
            "            var c = 1.70158;\n" +
            "            return 1 > (a /= 0.5) ? 0.5 * a * a * (((c *= 1.525) + 1) * a - c) : 0.5 * ((a -= 2) * a * (((c *= 1.525) + 1) * a + c) + 2)\n" +
            "        }, easeInBounce: function (a) {\n" +
            "            return 1 - B.easeOutBounce(1 - a)\n" +
            "        }, easeOutBounce: function (a) {\n" +
            "            return(a /= 1) < 1 / 2.75 ? 1 * 7.5625 * a * a : a < 2 / 2.75 ? 1 * (7.5625 * (a -= 1.5 / 2.75) *\n" +
            "                    a + 0.75) : a < 2.5 / 2.75 ? 1 * (7.5625 * (a -= 2.25 / 2.75) * a + 0.9375) : 1 * (7.5625 * (a -= 2.625 / 2.75) * a + 0.984375)\n" +
            "        }, easeInOutBounce: function (a) {\n" +
            "            return 0.5 > a ? 0.5 * B.easeInBounce(2 * a) : 0.5 * B.easeOutBounce(2 * a - 1) + 0.5\n" +
            "        }}, q = s.canvas.width, u = s.canvas.height;\n" +
            "        window.devicePixelRatio && (s.canvas.style.width = q + \"px\", s.canvas.style.height = u + \"px\", s.canvas.height = u * window.devicePixelRatio, s.canvas.width = q * window.devicePixelRatio, s.scale(window.devicePixelRatio, window.devicePixelRatio));\n" +
            "        this.PolarArea = function (a, c) {\n" +
            "            r.PolarArea.defaults = {scaleOverlay: !0,\n" +
            "                scaleOverride: !1, scaleSteps: null, scaleStepWidth: null, scaleStartValue: null, scaleShowLine: !0, scaleLineColor: \"rgba(0,0,0,.1)\", scaleLineWidth: 1, scaleShowLabels: !0, scaleLabel: \"<%=value%>\", scaleFontFamily: \"\\'Arial\\'\", scaleFontSize: 12, scaleFontStyle: \"normal\", scaleFontColor: \"#666\", scaleShowLabelBackdrop: !0, scaleBackdropColor: \"rgba(255,255,255,0.75)\", scaleBackdropPaddingY: 2, scaleBackdropPaddingX: 2, segmentShowStroke: !0, segmentStrokeColor: \"#fff\", segmentStrokeWidth: 2, animation: !0, animationSteps: 100, animationEasing: \"easeOutBounce\",\n" +
            "                animateRotate: !0, animateScale: !1, onAnimationComplete: null};\n" +
            "            var b = c ? y(r.PolarArea.defaults, c) : r.PolarArea.defaults;\n" +
            "            return new G(a, b, s)\n" +
            "        };\n" +
            "        this.Radar = function (a, c) {\n" +
            "            r.Radar.defaults = {scaleOverlay: !1, scaleOverride: !1, scaleSteps: null, scaleStepWidth: null, scaleStartValue: null, scaleShowLine: !0, scaleLineColor: \"rgba(0,0,0,.1)\", scaleLineWidth: 1, scaleShowLabels: !1, scaleLabel: \"<%=value%>\", scaleFontFamily: \"\\'Arial\\'\", scaleFontSize: 12, scaleFontStyle: \"normal\", scaleFontColor: \"#666\", scaleShowLabelBackdrop: !0, scaleBackdropColor: \"rgba(255,255,255,0.75)\",\n" +
            "                scaleBackdropPaddingY: 2, scaleBackdropPaddingX: 2, angleShowLineOut: !0, angleLineColor: \"rgba(0,0,0,.1)\", angleLineWidth: 1, pointLabelFontFamily: \"\\'Arial\\'\", pointLabelFontStyle: \"normal\", pointLabelFontSize: 12, pointLabelFontColor: \"#666\", pointDot: !0, pointDotRadius: 3, pointDotStrokeWidth: 1, datasetStroke: !0, datasetStrokeWidth: 2, datasetFill: !0, animation: !0, animationSteps: 60, animationEasing: \"easeOutQuart\", onAnimationComplete: null};\n" +
            "            var b = c ? y(r.Radar.defaults, c) : r.Radar.defaults;\n" +
            "            return new H(a, b, s)\n" +
            "        };\n" +
            "        this.Pie = function (a, c) {\n" +
            "            r.Pie.defaults = {segmentShowStroke: !0, segmentStrokeColor: \"#fff\", segmentStrokeWidth: 2, animation: !0, animationSteps: 100, animationEasing: \"easeOutBounce\", animateRotate: !0, animateScale: !1, onAnimationComplete: null};\n" +
            "            var b = c ? y(r.Pie.defaults, c) : r.Pie.defaults;\n" +
            "            return new I(a, b, s)\n" +
            "        };\n" +
            "        this.Doughnut = function (a, c) {\n" +
            "            r.Doughnut.defaults = {segmentShowStroke: !0, segmentStrokeColor: \"#fff\", segmentStrokeWidth: 2, percentageInnerCutout: 50, animation: !0, animationSteps: 100, animationEasing: \"easeOutBounce\", animateRotate: !0, animateScale: !1,\n" +
            "                onAnimationComplete: null};\n" +
            "            var b = c ? y(r.Doughnut.defaults, c) : r.Doughnut.defaults;\n" +
            "            return new J(a, b, s)\n" +
            "        };\n" +
            "        this.Line = function (a, c) {\n" +
            "            r.Line.defaults = {scaleOverlay: !1, scaleOverride: !1, scaleSteps: null, scaleStepWidth: null, scaleStartValue: null, scaleLineColor: \"rgba(0,0,0,.1)\", scaleLineWidth: 1, scaleShowLabels: !0, scaleLabel: \"<%=value%>\", scaleFontFamily: \"\\'Arial\\'\", scaleFontSize: 12, scaleFontStyle: \"normal\", scaleFontColor: \"#666\", scaleShowGridLines: !0, scaleGridLineColor: \"rgba(0,0,0,.05)\", scaleGridLineWidth: 1, bezierCurve: !0,\n" +
            "                pointDot: !0, pointDotRadius: 4, pointDotStrokeWidth: 2, datasetStroke: !0, datasetStrokeWidth: 2, datasetFill: !0, animation: !0, animationSteps: 60, animationEasing: \"easeOutQuart\", onAnimationComplete: null};\n" +
            "            var b = c ? y(r.Line.defaults, c) : r.Line.defaults;\n" +
            "            return new K(a, b, s)\n" +
            "        };\n" +
            "        this.Bar = function (a, c) {\n" +
            "            r.Bar.defaults = {scaleOverlay: !1, scaleOverride: !1, scaleSteps: null, scaleStepWidth: null, scaleStartValue: null, scaleLineColor: \"rgba(0,0,0,.1)\", scaleLineWidth: 1, scaleShowLabels: !0, scaleLabel: \"<%=value%>\", scaleFontFamily: \"\\'Arial\\'\",\n" +
            "                scaleFontSize: 12, scaleFontStyle: \"normal\", scaleFontColor: \"#666\", scaleShowGridLines: !0, scaleGridLineColor: \"rgba(0,0,0,.05)\", scaleGridLineWidth: 1, barShowStroke: !0, barStrokeWidth: 2, barValueSpacing: 5, barDatasetSpacing: 1, animation: !0, animationSteps: 60, animationEasing: \"easeOutQuart\", onAnimationComplete: null};\n" +
            "            var b = c ? y(r.Bar.defaults, c) : r.Bar.defaults;\n" +
            "            return new L(a, b, s)\n" +
            "        };\n" +
            "        var G = function (a, c, b) {\n" +
            "            var e, h, f, d, g, k, j, l, m;\n" +
            "            g = Math.min.apply(Math, [q, u]) / 2;\n" +
            "            g -= Math.max.apply(Math, [0.5 * c.scaleFontSize, 0.5 * c.scaleLineWidth]);\n" +
            "            d = 2 * c.scaleFontSize;\n" +
            "            c.scaleShowLabelBackdrop && (d += 2 * c.scaleBackdropPaddingY, g -= 1.5 * c.scaleBackdropPaddingY);\n" +
            "            l = g;\n" +
            "            d = d ? d : 5;\n" +
            "            e = Number.MIN_VALUE;\n" +
            "            h = Number.MAX_VALUE;\n" +
            "            for (f = 0; f < a.length; f++)a[f].value > e && (e = a[f].value), a[f].value < h && (h = a[f].value);\n" +
            "            f = Math.floor(l / (0.66 * d));\n" +
            "            d = Math.floor(0.5 * (l / d));\n" +
            "            m = c.scaleShowLabels ? c.scaleLabel : null;\n" +
            "            c.scaleOverride ? (j = {steps: c.scaleSteps, stepValue: c.scaleStepWidth, graphMin: c.scaleStartValue, labels: []}, z(m, j.labels, j.steps, c.scaleStartValue, c.scaleStepWidth)) : j = C(l, f, d, e, h,\n" +
            "                    m);\n" +
            "            k = g / j.steps;\n" +
            "            x(c, function () {\n" +
            "                for (var a = 0; a < j.steps; a++)if (c.scaleShowLine && (b.beginPath(), b.arc(q / 2, u / 2, k * (a + 1), 0, 2 * Math.PI, !0), b.strokeStyle = c.scaleLineColor, b.lineWidth = c.scaleLineWidth, b.stroke()), c.scaleShowLabels) {\n" +
            "                    b.textAlign = \"center\";\n" +
            "                    b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily;\n" +
            "                    var e = j.labels[a];\n" +
            "                    if (c.scaleShowLabelBackdrop) {\n" +
            "                        var d = b.measureText(e).width;\n" +
            "                        b.fillStyle = c.scaleBackdropColor;\n" +
            "                        b.beginPath();\n" +
            "                        b.rect(Math.round(q / 2 - d / 2 - c.scaleBackdropPaddingX), Math.round(u / 2 - k * (a +\n" +
            "                                1) - 0.5 * c.scaleFontSize - c.scaleBackdropPaddingY), Math.round(d + 2 * c.scaleBackdropPaddingX), Math.round(c.scaleFontSize + 2 * c.scaleBackdropPaddingY));\n" +
            "                        b.fill()\n" +
            "                    }\n" +
            "                    b.textBaseline = \"middle\";\n" +
            "                    b.fillStyle = c.scaleFontColor;\n" +
            "                    b.fillText(e, q / 2, u / 2 - k * (a + 1))\n" +
            "                }\n" +
            "            }, function (e) {\n" +
            "                var d = -Math.PI / 2, g = 2 * Math.PI / a.length, f = 1, h = 1;\n" +
            "                c.animation && (c.animateScale && (f = e), c.animateRotate && (h = e));\n" +
            "                for (e = 0; e < a.length; e++)b.beginPath(), b.arc(q / 2, u / 2, f * v(a[e].value, j, k), d, d + h * g, !1), b.lineTo(q / 2, u / 2), b.closePath(), b.fillStyle = a[e].color, b.fill(),\n" +
            "                        c.segmentShowStroke && (b.strokeStyle = c.segmentStrokeColor, b.lineWidth = c.segmentStrokeWidth, b.stroke()), d += h * g\n" +
            "            }, b)\n" +
            "        }, H = function (a, c, b) {\n" +
            "            var e, h, f, d, g, k, j, l, m;\n" +
            "            a.labels || (a.labels = []);\n" +
            "            g = Math.min.apply(Math, [q, u]) / 2;\n" +
            "            d = 2 * c.scaleFontSize;\n" +
            "            for (e = l = 0; e < a.labels.length; e++)b.font = c.pointLabelFontStyle + \" \" + c.pointLabelFontSize + \"px \" + c.pointLabelFontFamily, h = b.measureText(a.labels[e]).width, h > l && (l = h);\n" +
            "            g -= Math.max.apply(Math, [l, 1.5 * (c.pointLabelFontSize / 2)]);\n" +
            "            g -= c.pointLabelFontSize;\n" +
            "            l = g = A(g, null, 0);\n" +
            "            d = d ? d : 5;\n" +
            "            e = Number.MIN_VALUE;\n" +
            "            h = Number.MAX_VALUE;\n" +
            "            for (f = 0; f < a.datasets.length; f++)for (m = 0; m < a.datasets[f].data.length; m++)a.datasets[f].data[m] > e && (e = a.datasets[f].data[m]), a.datasets[f].data[m] < h && (h = a.datasets[f].data[m]);\n" +
            "            f = Math.floor(l / (0.66 * d));\n" +
            "            d = Math.floor(0.5 * (l / d));\n" +
            "            m = c.scaleShowLabels ? c.scaleLabel : null;\n" +
            "            c.scaleOverride ? (j = {steps: c.scaleSteps, stepValue: c.scaleStepWidth, graphMin: c.scaleStartValue, labels: []}, z(m, j.labels, j.steps, c.scaleStartValue, c.scaleStepWidth)) : j = C(l, f, d, e, h, m);\n" +
            "            k = g / j.steps;\n" +
            "            x(c, function () {\n" +
            "                var e = 2 * Math.PI /\n" +
            "                        a.datasets[0].data.length;\n" +
            "                b.save();\n" +
            "                b.translate(q / 2, u / 2);\n" +
            "                if (c.angleShowLineOut) {\n" +
            "                    b.strokeStyle = c.angleLineColor;\n" +
            "                    b.lineWidth = c.angleLineWidth;\n" +
            "                    for (var d = 0; d < a.datasets[0].data.length; d++)b.rotate(e), b.beginPath(), b.moveTo(0, 0), b.lineTo(0, -g), b.stroke()\n" +
            "                }\n" +
            "                for (d = 0; d < j.steps; d++) {\n" +
            "                    b.beginPath();\n" +
            "                    if (c.scaleShowLine) {\n" +
            "                        b.strokeStyle = c.scaleLineColor;\n" +
            "                        b.lineWidth = c.scaleLineWidth;\n" +
            "                        b.moveTo(0, -k * (d + 1));\n" +
            "                        for (var f = 0; f < a.datasets[0].data.length; f++)b.rotate(e), b.lineTo(0, -k * (d + 1));\n" +
            "                        b.closePath();\n" +
            "                        b.stroke()\n" +
            "                    }\n" +
            "                    c.scaleShowLabels &&\n" +
            "                    (b.textAlign = \"center\", b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily, b.textBaseline = \"middle\", c.scaleShowLabelBackdrop && (f = b.measureText(j.labels[d]).width, b.fillStyle = c.scaleBackdropColor, b.beginPath(), b.rect(Math.round(-f / 2 - c.scaleBackdropPaddingX), Math.round(-k * (d + 1) - 0.5 * c.scaleFontSize - c.scaleBackdropPaddingY), Math.round(f + 2 * c.scaleBackdropPaddingX), Math.round(c.scaleFontSize + 2 * c.scaleBackdropPaddingY)), b.fill()), b.fillStyle = c.scaleFontColor, b.fillText(j.labels[d], 0, -k * (d +\n" +
            "                            1)))\n" +
            "                }\n" +
            "                for (d = 0; d < a.labels.length; d++) {\n" +
            "                    b.font = c.pointLabelFontStyle + \" \" + c.pointLabelFontSize + \"px \" + c.pointLabelFontFamily;\n" +
            "                    b.fillStyle = c.pointLabelFontColor;\n" +
            "                    var f = Math.sin(e * d) * (g + c.pointLabelFontSize), h = Math.cos(e * d) * (g + c.pointLabelFontSize);\n" +
            "                    b.textAlign = e * d == Math.PI || 0 == e * d ? \"center\" : e * d > Math.PI ? \"right\" : \"left\";\n" +
            "                    b.textBaseline = \"middle\";\n" +
            "                    b.fillText(a.labels[d], f, -h)\n" +
            "                }\n" +
            "                b.restore()\n" +
            "            }, function (d) {\n" +
            "                var e = 2 * Math.PI / a.datasets[0].data.length;\n" +
            "                b.save();\n" +
            "                b.translate(q / 2, u / 2);\n" +
            "                for (var g = 0; g < a.datasets.length; g++) {\n" +
            "                    b.beginPath();\n" +
            "                    b.moveTo(0, d * -1 * v(a.datasets[g].data[0], j, k));\n" +
            "                    for (var f = 1; f < a.datasets[g].data.length; f++)b.rotate(e), b.lineTo(0, d * -1 * v(a.datasets[g].data[f], j, k));\n" +
            "                    b.closePath();\n" +
            "                    b.fillStyle = a.datasets[g].fillColor;\n" +
            "                    b.strokeStyle = a.datasets[g].strokeColor;\n" +
            "                    b.lineWidth = c.datasetStrokeWidth;\n" +
            "                    b.fill();\n" +
            "                    b.stroke();\n" +
            "                    if (c.pointDot) {\n" +
            "                        b.fillStyle = a.datasets[g].pointColor;\n" +
            "                        b.strokeStyle = a.datasets[g].pointStrokeColor;\n" +
            "                        b.lineWidth = c.pointDotStrokeWidth;\n" +
            "                        for (f = 0; f < a.datasets[g].data.length; f++)b.rotate(e), b.beginPath(), b.arc(0, d * -1 *\n" +
            "                                v(a.datasets[g].data[f], j, k), c.pointDotRadius, 2 * Math.PI, !1), b.fill(), b.stroke()\n" +
            "                    }\n" +
            "                    b.rotate(e)\n" +
            "                }\n" +
            "                b.restore()\n" +
            "            }, b)\n" +
            "        }, I = function (a, c, b) {\n" +
            "            for (var e = 0, h = Math.min.apply(Math, [u / 2, q / 2]) - 5, f = 0; f < a.length; f++)e += a[f].value;\n" +
            "            x(c, null, function (d) {\n" +
            "                var g = -Math.PI / 2, f = 1, j = 1;\n" +
            "                c.animation && (c.animateScale && (f = d), c.animateRotate && (j = d));\n" +
            "                for (d = 0; d < a.length; d++) {\n" +
            "                    var l = j * a[d].value / e * 2 * Math.PI;\n" +
            "                    b.beginPath();\n" +
            "                    b.arc(q / 2, u / 2, f * h, g, g + l);\n" +
            "                    b.lineTo(q / 2, u / 2);\n" +
            "                    b.closePath();\n" +
            "                    b.fillStyle = a[d].color;\n" +
            "                    b.fill();\n" +
            "                    c.segmentShowStroke && (b.lineWidth =\n" +
            "                            c.segmentStrokeWidth, b.strokeStyle = c.segmentStrokeColor, b.stroke());\n" +
            "                    g += l\n" +
            "                }\n" +
            "            }, b)\n" +
            "        }, J = function (a, c, b) {\n" +
            "            for (var e = 0, h = Math.min.apply(Math, [u / 2, q / 2]) - 5, f = h * (c.percentageInnerCutout / 100), d = 0; d < a.length; d++)e += a[d].value;\n" +
            "            x(c, null, function (d) {\n" +
            "                var k = -Math.PI / 2, j = 1, l = 1;\n" +
            "                c.animation && (c.animateScale && (j = d), c.animateRotate && (l = d));\n" +
            "                for (d = 0; d < a.length; d++) {\n" +
            "                    var m = l * a[d].value / e * 2 * Math.PI;\n" +
            "                    b.beginPath();\n" +
            "                    b.arc(q / 2, u / 2, j * h, k, k + m, !1);\n" +
            "                    b.arc(q / 2, u / 2, j * f, k + m, k, !0);\n" +
            "                    b.closePath();\n" +
            "                    b.fillStyle = a[d].color;\n" +
            "                    b.fill();\n" +
            "                    c.segmentShowStroke &&\n" +
            "                    (b.lineWidth = c.segmentStrokeWidth, b.strokeStyle = c.segmentStrokeColor, b.stroke());\n" +
            "                    k += m\n" +
            "                }\n" +
            "            }, b)\n" +
            "        }, K = function (a, c, b) {\n" +
            "            var e, h, f, d, g, k, j, l, m, t, r, n, p, s = 0;\n" +
            "            g = u;\n" +
            "            b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily;\n" +
            "            t = 1;\n" +
            "            for (d = 0; d < a.labels.length; d++)e = b.measureText(a.labels[d]).width, t = e > t ? e : t;\n" +
            "            q / a.labels.length < t ? (s = 45, q / a.labels.length < Math.cos(s) * t ? (s = 90, g -= t) : g -= Math.sin(s) * t) : g -= c.scaleFontSize;\n" +
            "            d = c.scaleFontSize;\n" +
            "            g = g - 5 - d;\n" +
            "            e = Number.MIN_VALUE;\n" +
            "            h = Number.MAX_VALUE;\n" +
            "            for (f = 0; f < a.datasets.length; f++)for (l =\n" +
            "                                                                0; l < a.datasets[f].data.length; l++)a.datasets[f].data[l] > e && (e = a.datasets[f].data[l]), a.datasets[f].data[l] < h && (h = a.datasets[f].data[l]);\n" +
            "            f = Math.floor(g / (0.66 * d));\n" +
            "            d = Math.floor(0.5 * (g / d));\n" +
            "            l = c.scaleShowLabels ? c.scaleLabel : \"\";\n" +
            "            c.scaleOverride ? (j = {steps: c.scaleSteps, stepValue: c.scaleStepWidth, graphMin: c.scaleStartValue, labels: []}, z(l, j.labels, j.steps, c.scaleStartValue, c.scaleStepWidth)) : j = C(g, f, d, e, h, l);\n" +
            "            k = Math.floor(g / j.steps);\n" +
            "            d = 1;\n" +
            "            if (c.scaleShowLabels) {\n" +
            "                b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily;\n" +
            "                for (e = 0; e < j.labels.length; e++)h = b.measureText(j.labels[e]).width, d = h > d ? h : d;\n" +
            "                d += 10\n" +
            "            }\n" +
            "            r = q - d - t;\n" +
            "            m = Math.floor(r / (a.labels.length - 1));\n" +
            "            n = q - t / 2 - r;\n" +
            "            p = g + c.scaleFontSize / 2;\n" +
            "            x(c, function () {\n" +
            "                b.lineWidth = c.scaleLineWidth;\n" +
            "                b.strokeStyle = c.scaleLineColor;\n" +
            "                b.beginPath();\n" +
            "                b.moveTo(q - t / 2 + 5, p);\n" +
            "                b.lineTo(q - t / 2 - r - 5, p);\n" +
            "                b.stroke();\n" +
            "                0 < s ? (b.save(), b.textAlign = \"right\") : b.textAlign = \"center\";\n" +
            "                b.fillStyle = c.scaleFontColor;\n" +
            "                for (var d = 0; d < a.labels.length; d++)b.save(), 0 < s ? (b.translate(n + d * m, p + c.scaleFontSize), b.rotate(-(s * (Math.PI / 180))), b.fillText(a.labels[d],\n" +
            "                        0, 0), b.restore()) : b.fillText(a.labels[d], n + d * m, p + c.scaleFontSize + 3), b.beginPath(), b.moveTo(n + d * m, p + 3), c.scaleShowGridLines && 0 < d ? (b.lineWidth = c.scaleGridLineWidth, b.strokeStyle = c.scaleGridLineColor, b.lineTo(n + d * m, 5)) : b.lineTo(n + d * m, p + 3), b.stroke();\n" +
            "                b.lineWidth = c.scaleLineWidth;\n" +
            "                b.strokeStyle = c.scaleLineColor;\n" +
            "                b.beginPath();\n" +
            "                b.moveTo(n, p + 5);\n" +
            "                b.lineTo(n, 5);\n" +
            "                b.stroke();\n" +
            "                b.textAlign = \"right\";\n" +
            "                b.textBaseline = \"middle\";\n" +
            "                for (d = 0; d < j.steps; d++)b.beginPath(), b.moveTo(n - 3, p - (d + 1) * k), c.scaleShowGridLines ? (b.lineWidth = c.scaleGridLineWidth,\n" +
            "                        b.strokeStyle = c.scaleGridLineColor, b.lineTo(n + r + 5, p - (d + 1) * k)) : b.lineTo(n - 0.5, p - (d + 1) * k), b.stroke(), c.scaleShowLabels && b.fillText(j.labels[d], n - 8, p - (d + 1) * k)\n" +
            "            }, function (d) {\n" +
            "                function e(b, c) {\n" +
            "                    return p - d * v(a.datasets[b].data[c], j, k)\n" +
            "                }\n" +
            "\n" +
            "                for (var f = 0; f < a.datasets.length; f++) {\n" +
            "                    b.strokeStyle = a.datasets[f].strokeColor;\n" +
            "                    b.lineWidth = c.datasetStrokeWidth;\n" +
            "                    b.beginPath();\n" +
            "                    b.moveTo(n, p - d * v(a.datasets[f].data[0], j, k));\n" +
            "                    for (var g = 1; g < a.datasets[f].data.length; g++)c.bezierCurve ? b.bezierCurveTo(n + m * (g - 0.5), e(f, g - 1), n + m * (g - 0.5),\n" +
            "                            e(f, g), n + m * g, e(f, g)) : b.lineTo(n + m * g, e(f, g));\n" +
            "                    b.stroke();\n" +
            "                    c.datasetFill ? (b.lineTo(n + m * (a.datasets[f].data.length - 1), p), b.lineTo(n, p), b.closePath(), b.fillStyle = a.datasets[f].fillColor, b.fill()) : b.closePath();\n" +
            "                    if (c.pointDot) {\n" +
            "                        b.fillStyle = a.datasets[f].pointColor;\n" +
            "                        b.strokeStyle = a.datasets[f].pointStrokeColor;\n" +
            "                        b.lineWidth = c.pointDotStrokeWidth;\n" +
            "                        for (g = 0; g < a.datasets[f].data.length; g++)b.beginPath(), b.arc(n + m * g, p - d * v(a.datasets[f].data[g], j, k), c.pointDotRadius, 0, 2 * Math.PI, !0), b.fill(), b.stroke()\n" +
            "                    }\n" +
            "                }\n" +
            "            }, b)\n" +
            "        }, L = function (a, c, b) {\n" +
            "            var e, h, f, d, g, k, j, l, m, t, r, n, p, s, w = 0;\n" +
            "            g = u;\n" +
            "            b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily;\n" +
            "            t = 1;\n" +
            "            for (d = 0; d < a.labels.length; d++)e = b.measureText(a.labels[d]).width, t = e > t ? e : t;\n" +
            "            q / a.labels.length < t ? (w = 45, q / a.labels.length < Math.cos(w) * t ? (w = 90, g -= t) : g -= Math.sin(w) * t) : g -= c.scaleFontSize;\n" +
            "            d = c.scaleFontSize;\n" +
            "            g = g - 5 - d;\n" +
            "            e = Number.MIN_VALUE;\n" +
            "            h = Number.MAX_VALUE;\n" +
            "            for (f = 0; f < a.datasets.length; f++)for (l = 0; l < a.datasets[f].data.length; l++)a.datasets[f].data[l] > e && (e = a.datasets[f].data[l]), a.datasets[f].data[l] <\n" +
            "                    h && (h = a.datasets[f].data[l]);\n" +
            "            f = Math.floor(g / (0.66 * d));\n" +
            "            d = Math.floor(0.5 * (g / d));\n" +
            "            l = c.scaleShowLabels ? c.scaleLabel : \"\";\n" +
            "            c.scaleOverride ? (j = {steps: c.scaleSteps, stepValue: c.scaleStepWidth, graphMin: c.scaleStartValue, labels: []}, z(l, j.labels, j.steps, c.scaleStartValue, c.scaleStepWidth)) : j = C(g, f, d, e, h, l);\n" +
            "            k = Math.floor(g / j.steps);\n" +
            "            d = 1;\n" +
            "            if (c.scaleShowLabels) {\n" +
            "                b.font = c.scaleFontStyle + \" \" + c.scaleFontSize + \"px \" + c.scaleFontFamily;\n" +
            "                for (e = 0; e < j.labels.length; e++)h = b.measureText(j.labels[e]).width, d = h > d ? h : d;\n" +
            "                d += 10\n" +
            "            }\n" +
            "            r = q - d - t;\n" +
            "            m =\n" +
            "                    Math.floor(r / a.labels.length);\n" +
            "            s = (m - 2 * c.scaleGridLineWidth - 2 * c.barValueSpacing - (c.barDatasetSpacing * a.datasets.length - 1) - (c.barStrokeWidth / 2 * a.datasets.length - 1)) / a.datasets.length;\n" +
            "            n = q - t / 2 - r;\n" +
            "            p = g + c.scaleFontSize / 2;\n" +
            "            x(c, function () {\n" +
            "                b.lineWidth = c.scaleLineWidth;\n" +
            "                b.strokeStyle = c.scaleLineColor;\n" +
            "                b.beginPath();\n" +
            "                b.moveTo(q - t / 2 + 5, p);\n" +
            "                b.lineTo(q - t / 2 - r - 5, p);\n" +
            "                b.stroke();\n" +
            "                0 < w ? (b.save(), b.textAlign = \"right\") : b.textAlign = \"center\";\n" +
            "                b.fillStyle = c.scaleFontColor;\n" +
            "                for (var d = 0; d < a.labels.length; d++)b.save(), 0 < w ? (b.translate(n +\n" +
            "                        d * m, p + c.scaleFontSize), b.rotate(-(w * (Math.PI / 180))), b.fillText(a.labels[d], 0, 0), b.restore()) : b.fillText(a.labels[d], n + d * m + m / 2, p + c.scaleFontSize + 3), b.beginPath(), b.moveTo(n + (d + 1) * m, p + 3), b.lineWidth = c.scaleGridLineWidth, b.strokeStyle = c.scaleGridLineColor, b.lineTo(n + (d + 1) * m, 5), b.stroke();\n" +
            "                b.lineWidth = c.scaleLineWidth;\n" +
            "                b.strokeStyle = c.scaleLineColor;\n" +
            "                b.beginPath();\n" +
            "                b.moveTo(n, p + 5);\n" +
            "                b.lineTo(n, 5);\n" +
            "                b.stroke();\n" +
            "                b.textAlign = \"right\";\n" +
            "                b.textBaseline = \"middle\";\n" +
            "                for (d = 0; d < j.steps; d++)b.beginPath(), b.moveTo(n - 3, p - (d + 1) *\n" +
            "                        k), c.scaleShowGridLines ? (b.lineWidth = c.scaleGridLineWidth, b.strokeStyle = c.scaleGridLineColor, b.lineTo(n + r + 5, p - (d + 1) * k)) : b.lineTo(n - 0.5, p - (d + 1) * k), b.stroke(), c.scaleShowLabels && b.fillText(j.labels[d], n - 8, p - (d + 1) * k)\n" +
            "            }, function (d) {\n" +
            "                b.lineWidth = c.barStrokeWidth;\n" +
            "                for (var e = 0; e < a.datasets.length; e++) {\n" +
            "                    b.fillStyle = a.datasets[e].fillColor;\n" +
            "                    b.strokeStyle = a.datasets[e].strokeColor;\n" +
            "                    for (var f = 0; f < a.datasets[e].data.length; f++) {\n" +
            "                        var g = n + c.barValueSpacing + m * f + s * e + c.barDatasetSpacing * e + c.barStrokeWidth * e;\n" +
            "                        b.beginPath();\n" +
            "                        b.moveTo(g, p);\n" +
            "                        b.lineTo(g, p - d * v(a.datasets[e].data[f], j, k) + c.barStrokeWidth / 2);\n" +
            "                        b.lineTo(g + s, p - d * v(a.datasets[e].data[f], j, k) + c.barStrokeWidth / 2);\n" +
            "                        b.lineTo(g + s, p);\n" +
            "                        c.barShowStroke && b.stroke();\n" +
            "                        b.closePath();\n" +
            "                        b.fill()\n" +
            "                    }\n" +
            "                }\n" +
            "            }, b)\n" +
            "        }, D = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (a) {\n" +
            "            window.setTimeout(a, 1E3 / 60)\n" +
            "        }, F = {}\n" +
            "    };    </script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<canvas id=\"ticketChart\" width=\"600\" height=\"400\"></canvas>\n" +
            "<script>\n" +
            "\n" +
            "    var ctx = document.getElementById(\"ticketChart\").getContext(\"2d\");\n" +
            "\n" +
            "    var studentData = {\n" +
            "        fillColor : \"rgba(200,0,0,0.1)\",\n" +
            "        strokeColor : \"rgba(200,0,0,1)\",\n" +
            "        pointColor : \"rgba(200,0,0,1)\",\n" +
            "        pointStrokeColor : \"#fff\",\n" +
            "        data : [46,71,76,71]\n" +
            "    };\n" +
            "    var baseincomeData =  {\n" +
            "        fillColor : \"rgba(0,200,0,0.1)\",\n" +
            "        strokeColor : \"rgba(0,200,0,1)\",\n" +
            "        pointColor : \"rgba(0,200,0,1)\",\n" +
            "        pointStrokeColor : \"#fff\",\n" +
            "        data : [46,60,64,67]\n" +
            "    };\n" +
            "\n" +
            "    var highincomeData = {\n" +
            "        fillColor : \"rgba(0,0,200,0.1)\",\n" +
            "        strokeColor : \"rgba(0,0,200,1)\",\n" +
            "        pointColor : \"rgba(0,0,200,1)\",\n" +
            "        pointStrokeColor : \"#fff\",\n" +
            "        data : [57,70,78,78]\n" +
            "    };\n" +
            "\n" +
            "    var ticketData = {\n" +
            "        labels : [\"2008\",\"2009\",\"2010\",\"2011\"],\n" +
            "        datasets : [studentData,baseincomeData,highincomeData]\n" +
            "    };\n" +
            "\n" +
            "    var ticketChart = new Chart(ctx).Line(ticketData);\n" +
            "\n" +
            "    alert('done!');\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";

    String javascript = "$scope.accordion = { sections: [{ title: \"Performance globale\", table: [{ combined: \"Cumulé\", meterReadingLast: \"%LAST_READING_DATE%\", meterReadingPrevious: \"%PREVIOUS_READING_DATE%\"}, { label: \"Ecart total\", combined: \"%COMBINED_LINE_01%\", meterReadingLast: \"%LAST_READING_VAL_01%\", meterReadingPrevious: \"%PREV_READING_VAL_01%\"}]}\n";

    @Test
    public void testToMelWriteTextLine() throws Exception {
        String res = toMelWriteTextLines("$local:connectionId", html);
        assertNotNull(res);
    }

    @Test
    public void testWriteTextWithReplacements() {
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("%LAST_READING_DATE%", "tab['lastReadingDate']");
        replacements.put("%PREVIOUS_READING_DATE%", "tab['previousReadingDate']");

        String generatedMel = MelHelper.toMelWriteTextLineWithReplacements("connectionId", javascript, replacements);

        System.out.println("generatedMel = " + generatedMel);

        Assert.assertTrue(generatedMel.contains("tab['lastReadingDate']"));
        Assert.assertFalse(generatedMel.contains("\"tab['lastReadingDate']\""));
        Assert.assertTrue(generatedMel.contains("%PREVIOUS_READING_DATE%"));
    }

    @Test
    public void testGetMelTemplate() {
        String melTemplateToCreateFile = MelHelper.getMelTemplateToCreateFile();
        assertNotNull(melTemplateToCreateFile);
        assertFalse(melTemplateToCreateFile.isEmpty());
    }


}
