
var PageName = 'Home';
var PageId = 'd0ff7d7c25234cf9abf2f4a68c21a620'
var PageUrl = 'Home.html'
document.title = 'Home';
var PageNotes = 
{
"pageName":"Home",
"showNotesNames":"False"}
var $OnLoadVariable = '';

var $CSUM;

var hasQuery = false;
var query = window.location.hash.substring(1);
if (query.length > 0) hasQuery = true;
var vars = query.split("&");
for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=");
    if (pair[0].length > 0) eval("$" + pair[0] + " = decodeURIComponent(pair[1]);");
} 

if (hasQuery && $CSUM != 1) {
alert('Prototype Warning: The variable values were too long to pass to this page.\nIf you are using IE, using Firefox will support more data.');
}

function GetQuerystring() {
    return '#OnLoadVariable=' + encodeURIComponent($OnLoadVariable) + '&CSUM=1';
}

function PopulateVariables(value) {
    var d = new Date();
  value = value.replace(/\[\[OnLoadVariable\]\]/g, $OnLoadVariable);
  value = value.replace(/\[\[PageName\]\]/g, PageName);
  value = value.replace(/\[\[GenDay\]\]/g, '15');
  value = value.replace(/\[\[GenMonth\]\]/g, '11');
  value = value.replace(/\[\[GenMonthName\]\]/g, 'novembre');
  value = value.replace(/\[\[GenDayOfWeek\]\]/g, 'mardi');
  value = value.replace(/\[\[GenYear\]\]/g, '2011');
  value = value.replace(/\[\[Day\]\]/g, d.getDate());
  value = value.replace(/\[\[Month\]\]/g, d.getMonth() + 1);
  value = value.replace(/\[\[MonthName\]\]/g, GetMonthString(d.getMonth()));
  value = value.replace(/\[\[DayOfWeek\]\]/g, GetDayString(d.getDay()));
  value = value.replace(/\[\[Year\]\]/g, d.getFullYear());
  return value;
}

function OnLoad(e) {

}

var u21 = document.getElementById('u21');
gv_vAlignTable['u21'] = 'center';
var u7 = document.getElementById('u7');
gv_vAlignTable['u7'] = 'center';
var u51 = document.getElementById('u51');
gv_vAlignTable['u51'] = 'center';
var u25 = document.getElementById('u25');

var u16 = document.getElementById('u16');

var u55 = document.getElementById('u55');
gv_vAlignTable['u55'] = 'center';
var u46 = document.getElementById('u46');

u46.style.cursor = 'pointer';
if (bIE) u46.attachEvent("onclick", Clicku46);
else u46.addEventListener("click", Clicku46, true);
function Clicku46(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd1u43','none','',500,'none','',500);

}

}

var u76 = document.getElementById('u76');
gv_vAlignTable['u76'] = 'center';
var u31 = document.getElementById('u31');
gv_vAlignTable['u31'] = 'center';
var u77 = document.getElementById('u77');

var u38 = document.getElementById('u38');
gv_vAlignTable['u38'] = 'center';
var u32 = document.getElementById('u32');

var u23 = document.getElementById('u23');

var u62 = document.getElementById('u62');

var u53 = document.getElementById('u53');
gv_vAlignTable['u53'] = 'center';
var u87 = document.getElementById('u87');
gv_vAlignTable['u87'] = 'center';
var u1 = document.getElementById('u1');
gv_vAlignTable['u1'] = 'center';
var u27 = document.getElementById('u27');
gv_vAlignTable['u27'] = 'top';
var u57 = document.getElementById('u57');
gv_vAlignTable['u57'] = 'center';
var u66 = document.getElementById('u66');
gv_vAlignTable['u66'] = 'center';
var u30 = document.getElementById('u30');

var u8 = document.getElementById('u8');
gv_vAlignTable['u8'] = 'top';
var u60 = document.getElementById('u60');

u60.style.cursor = 'pointer';
if (bIE) u60.attachEvent("onclick", Clicku60);
else u60.addEventListener("click", Clicku60, true);
function Clicku60(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd2u43','none','',500,'none','',500);

}

}

var u89 = document.getElementById('u89');
gv_vAlignTable['u89'] = 'center';
var u34 = document.getElementById('u34');

var u17 = document.getElementById('u17');

var u64 = document.getElementById('u64');

var u19 = document.getElementById('u19');

if (bIE) u19.attachEvent("onmouseout", MouseOutu19);
else u19.addEventListener("mouseout", MouseOutu19, true);
function MouseOutu19(e)
{
windowEvent = e;

if (!IsTrueMouseOut('u19',e)) return;
if (true) {

	SetPanelState('u16', 'pd0u16','none','',500,'none','',500);

}

}

var u49 = document.getElementById('u49');
gv_vAlignTable['u49'] = 'center';
var u79 = document.getElementById('u79');

var u81 = document.getElementById('u81');
gv_vAlignTable['u81'] = 'top';
var u85 = document.getElementById('u85');
gv_vAlignTable['u85'] = 'center';
var u11 = document.getElementById('u11');

var u41 = document.getElementById('u41');

var u71 = document.getElementById('u71');

u71.style.cursor = 'pointer';
if (bIE) u71.attachEvent("onclick", Clicku71);
else u71.addEventListener("click", Clicku71, true);
function Clicku71(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u64', 'pd0u64','none','',500,'none','',500);

}

}

var u15 = document.getElementById('u15');
gv_vAlignTable['u15'] = 'top';
var u45 = document.getElementById('u45');
gv_vAlignTable['u45'] = 'center';
var u36 = document.getElementById('u36');

var u75 = document.getElementById('u75');

u75.style.cursor = 'pointer';
if (bIE) u75.attachEvent("onclick", Clicku75);
else u75.addEventListener("click", Clicku75, true);
function Clicku75(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u64', 'pd1u64','none','',500,'none','',500);

}

}

var u58 = document.getElementById('u58');

u58.style.cursor = 'pointer';
if (bIE) u58.attachEvent("onclick", Clicku58);
else u58.addEventListener("click", Clicku58, true);
function Clicku58(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd1u43','none','',500,'none','',500);

}

}

var u37 = document.getElementById('u37');

var u2 = document.getElementById('u2');

var u83 = document.getElementById('u83');
gv_vAlignTable['u83'] = 'center';
var u22 = document.getElementById('u22');

u22.style.cursor = 'pointer';
if (bIE) u22.attachEvent("onclick", Clicku22);
else u22.addEventListener("click", Clicku22, true);
function Clicku22(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u16', 'pd1u16','none','',500,'none','',500);

}

}

if (bIE) u22.attachEvent("onmouseover", MouseOveru22);
else u22.addEventListener("mouseover", MouseOveru22, true);
function MouseOveru22(e)
{
windowEvent = e;

if (!IsTrueMouseOver('u22',e)) return;
if (true) {

	SetPanelState('u16', 'pd1u16','none','',500,'none','',500);

}

}

var u13 = document.getElementById('u13');

var u52 = document.getElementById('u52');

u52.style.cursor = 'pointer';
if (bIE) u52.attachEvent("onclick", Clicku52);
else u52.addEventListener("click", Clicku52, true);
function Clicku52(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd1u43','none','',500,'none','',500);

}

}

var u43 = document.getElementById('u43');

var u0 = document.getElementById('u0');

var u3 = document.getElementById('u3');
gv_vAlignTable['u3'] = 'center';
var u47 = document.getElementById('u47');
gv_vAlignTable['u47'] = 'center';
var u68 = document.getElementById('u68');
gv_vAlignTable['u68'] = 'center';
var u86 = document.getElementById('u86');

var u73 = document.getElementById('u73');

u73.style.cursor = 'pointer';
if (bIE) u73.attachEvent("onclick", Clicku73);
else u73.addEventListener("click", Clicku73, true);
function Clicku73(e)
{
windowEvent = e;


if (true) {

}

}

var u84 = document.getElementById('u84');

var u20 = document.getElementById('u20');

var u50 = document.getElementById('u50');

u50.style.cursor = 'pointer';
if (bIE) u50.attachEvent("onclick", Clicku50);
else u50.addEventListener("click", Clicku50, true);
function Clicku50(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd0u43','none','',500,'none','',500);

}

}

var u28 = document.getElementById('u28');

var u24 = document.getElementById('u24');
gv_vAlignTable['u24'] = 'center';
var u54 = document.getElementById('u54');

u54.style.cursor = 'pointer';
if (bIE) u54.attachEvent("onclick", Clicku54);
else u54.addEventListener("click", Clicku54, true);
function Clicku54(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd2u43','none','',500,'none','',500);

}

}

var u39 = document.getElementById('u39');
gv_vAlignTable['u39'] = 'top';
var u69 = document.getElementById('u69');

u69.style.cursor = 'pointer';
if (bIE) u69.attachEvent("onclick", Clicku69);
else u69.addEventListener("click", Clicku69, true);
function Clicku69(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u64', 'pd1u64','none','',500,'none','',500);

}

}

var u78 = document.getElementById('u78');
gv_vAlignTable['u78'] = 'center';
var u4 = document.getElementById('u4');

var u6 = document.getElementById('u6');

var u61 = document.getElementById('u61');
gv_vAlignTable['u61'] = 'center';
var u35 = document.getElementById('u35');
gv_vAlignTable['u35'] = 'center';
var u26 = document.getElementById('u26');
gv_vAlignTable['u26'] = 'center';
var u65 = document.getElementById('u65');

u65.style.cursor = 'pointer';
if (bIE) u65.attachEvent("onclick", Clicku65);
else u65.addEventListener("click", Clicku65, true);
function Clicku65(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u64', 'pd0u64','none','',500,'none','',500);

}

}

var u56 = document.getElementById('u56');

u56.style.cursor = 'pointer';
if (bIE) u56.attachEvent("onclick", Clicku56);
else u56.addEventListener("click", Clicku56, true);
function Clicku56(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd0u43','none','',500,'none','',500);

}

}

var u82 = document.getElementById('u82');

var u5 = document.getElementById('u5');
gv_vAlignTable['u5'] = 'center';
var u12 = document.getElementById('u12');
gv_vAlignTable['u12'] = 'center';
var u9 = document.getElementById('u9');

var u42 = document.getElementById('u42');
gv_vAlignTable['u42'] = 'center';
var u33 = document.getElementById('u33');
gv_vAlignTable['u33'] = 'center';
var u72 = document.getElementById('u72');
gv_vAlignTable['u72'] = 'center';
var u63 = document.getElementById('u63');
gv_vAlignTable['u63'] = 'center';
var u18 = document.getElementById('u18');
gv_vAlignTable['u18'] = 'center';
var u48 = document.getElementById('u48');

u48.style.cursor = 'pointer';
if (bIE) u48.attachEvent("onclick", Clicku48);
else u48.addEventListener("click", Clicku48, true);
function Clicku48(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd2u43','none','',500,'none','',500);

}

}

var u67 = document.getElementById('u67');

u67.style.cursor = 'pointer';
if (bIE) u67.attachEvent("onclick", Clicku67);
else u67.addEventListener("click", Clicku67, true);
function Clicku67(e)
{
windowEvent = e;


if (true) {

}

}

var u88 = document.getElementById('u88');

var u80 = document.getElementById('u80');
gv_vAlignTable['u80'] = 'center';
var u10 = document.getElementById('u10');
gv_vAlignTable['u10'] = 'center';
var u40 = document.getElementById('u40');
gv_vAlignTable['u40'] = 'top';
var u70 = document.getElementById('u70');
gv_vAlignTable['u70'] = 'center';
var u14 = document.getElementById('u14');
gv_vAlignTable['u14'] = 'center';
var u44 = document.getElementById('u44');

u44.style.cursor = 'pointer';
if (bIE) u44.attachEvent("onclick", Clicku44);
else u44.addEventListener("click", Clicku44, true);
function Clicku44(e)
{
windowEvent = e;


if (true) {

	SetPanelState('u43', 'pd0u43','none','',500,'none','',500);

}

}

var u74 = document.getElementById('u74');
gv_vAlignTable['u74'] = 'center';
var u29 = document.getElementById('u29');
gv_vAlignTable['u29'] = 'center';
var u59 = document.getElementById('u59');
gv_vAlignTable['u59'] = 'center';
if (window.OnLoad) OnLoad();
