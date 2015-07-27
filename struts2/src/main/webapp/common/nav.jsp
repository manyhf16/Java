<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
          class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">百知教育</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Core Java 系列课程 <b
            class="caret"></b></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Web 系列课程 <b
            class="caret"></b></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Struts 2 <b
            class="caret"></b></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${root}/day1">day1</a></li>
            <li><a href="${root}/day2">day2</a></li>
            <li><a href="${root}/day3">day3</a></li>
            <li><a href="${root}/day4">day4</a></li>
            <li><a href="${root}/day5">day5</a></li>
            <li><a href="${root}/day6">day6</a></li>
            <li><a href="${root}/day7">day7</a></li>
            <li><a href="${root}/day8">day8</a></li>
            <li><a href="${root}/day9">day9</a></li>
          </ul></li>
      </ul>
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="请输入搜索项">
        </div>
        <button type="submit" class="btn btn-default">搜索</button>
      </form>
    </div>
    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container-fluid -->
</nav>
