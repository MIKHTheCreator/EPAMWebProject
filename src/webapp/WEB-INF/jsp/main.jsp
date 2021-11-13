<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html >
    <head>
      <meta charset="UTF-8">
      <title>Main Page</title>

      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
      <link rel="stylesheet" href="/../css/style.css">
      <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>
      <link rel="stylesheet" href="/../css/footer.css">

        <style>

          @import url(https://fonts.googleapis.com/css?family=Montserrat:400,700|Josefin+Sans:400,600,700,400italic,600italic);

            body{
                font-family: 'josefin sans';
                font-size: 16px;
            }

            .hero{
                height: 100vh;
                width: 100%;
                position: fixed;
                top: 0;
                z-index: 9;
                transition: all 1.6s cubic-bezier(0.86, 0, 0.07, 1);
            }
            .hero.scrolled{
                transform: translate3d(0, -100%, 0) scale(.75);
                opacity: 0;
            }
            .hero-inner{
                background-image: url('https://images.unsplash.com/photo-1607269911070-653c0ac22c5e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80');
                background-size: cover;
                background-position: center;
                display: table;
                width: 100%;
                height: 100vh;
                position: fixed;
                top: 0;
            }
            .hero-title{
                display: table-cell;
                vertical-align: middle;
                text-align: center;
            }
            h1, h2, h3, h4, h5, h6{
                font-family: montserrat;
            }
            .font-2{
                font-family: 'josefin sans';
                font-weight: 700;
            }
            .title{ letter-spacing: .3em; text-transform: uppercase; }
            .text-light{ color: #fff }
            .font-alt{
                font-family: 'georgia';
                font-style: italic;
                color: #666;
            }
            .hero{
                overflow: hidden;
                z-index: 1;
            }
            .content{
                position: relative;
                background-color: #fff;
                border-top: 10px solid black;
                padding: 0;
                margin: 0;
                transition: all 1.6s cubic-bezier(0.86, 0, 0.07, 1);
                transform: translate3d(0, 20px, 0) scale(.75);
                opacity: 0;
            }
            .content.scrolled{
                transform: translate3d(0, 0, 0) scale(1);
                opacity: 1;
            }
            .sd{
                color: #fff;
                position: absolute;
                bottom: 20px;
                left: 50%;
                transform: translateX(-50%);
            }
            .sd:hover, .sd:focus{
                color: #fff;
                opacity: .7;
                text-decoration: none;
            }
        </style>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>

    </head>

    <body>
          <div class="hero">
            <div class="hero-inner">
                    <div class="hero-title">
                        <h1 class="text-light title font-2">Globo Bank System</h1>
                        <p class="text-capitalize text-light">Here you can save your money</p>
                    </div>
                    <a href="#" class="sd">Scroll Down</a>
            </div>
        </div>
        <div class="content clearfix">
            <div class="container" style="padding-top: 60px">
                <div class="row">
                    <div class="col-md-5">
                        <h2 class="text-uppercase">Security</h2>
                        <p class="font-alt">A financial control center to suit your every need</p>
                    </div>
                    <div class="col-md-7">
                        <p style="padding-top: 47px">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam earum eos corporis totam vel, eaque sapiente officiis sint culpa inventore blanditiis hic cupiditate illo nam assumenda reprehenderit suscipit dolorum quibusdam.</p>
                    </div>
                </div>
            </div>
            <div class="container" style="padding-top: 60px">
                <div class="row">
                    <div class="col-md-5">
                        <h2 class="text-uppercase">Payments</h2>
                        <p class="font-alt">Your bank, your world</p>
                    </div>
                    <div class="col-md-7">
                        <p style="padding-top: 47px">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam earum eos corporis totam vel, eaque sapiente officiis sint culpa inventore blanditiis hic cupiditate illo nam assumenda reprehenderit suscipit dolorum quibusdam.</p>
                    </div>
                </div>
            </div>
            <div class="container" style="padding-top: 60px">
                <div class="row">
                    <div class="col-md-5">
                        <h2 class="text-uppercase">Speed</h2>
                        <p class="font-alt">A customer-focused bank that provides complete financial solutions to meet your entire financial needs</p>
                    </div>
                    <div class="col-md-7">
                        <p style="padding-top: 47px">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam earum eos corporis totam vel, eaque sapiente officiis sint culpa inventore blanditiis hic cupiditate illo nam assumenda reprehenderit suscipit dolorum quibusdam.</p>
                    </div>
                </div>
            </div>
            <div class="container" style="padding-top: 60px">
                <div class="row">
                    <div class="col-md-5">
                        <h2 class="text-uppercase">Quality</h2>
                        <p class="font-alt">We’re more than just someone’s ATM. We’re here for life’s big moments</p>
                    </div>
                    <div class="col-md-7">
                        <p style="padding-top: 47px">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam earum eos corporis totam vel, eaque sapiente officiis sint culpa inventore blanditiis hic cupiditate illo nam assumenda reprehenderit suscipit dolorum quibusdam.</p>
                    </div>
                </div>
            </div>
            <div class="container" style="height: 100px"></div>
        </div>

        <div class="footer-dark">
            <footer>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-md-3 item">
                            <h3>Services</h3>
                            <ul>
                                <li><a href="#">Web design</a></li>
                                <li><a href="#">Development</a></li>
                                <li><a href="#">Hosting</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-6 col-md-3 item">
                            <h3>About</h3>
                            <ul>
                                <li><a href="#">Company</a></li>
                                <li><a href="#">Team</a></li>
                                <li><a href="#">Careers</a></li>
                            </ul>
                        </div>
                        <div class="col-md-6 item text">
                            <h3>MIP</h3>
                            <p>Make it Perfect</p>
                        </div>
                        <div class="col item social"><a href="#"><i class="icon ion-social-facebook"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-instagram"></i></a></div>
                    </div>
                    <p class="copyright">MIP © 2021</p>
                </div>
            </footer>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>

        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>
        <script src='http://achtungthemes.com/gnoli/js/lib/scripts.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/smoothscroll/1.4.0/SmoothScroll.min.js'></script>

        <script src="/../scripts/index.js"></script>

    </body>
</html>