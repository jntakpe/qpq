/**
 * Grunt configuration file
 *
 * @param grunt
 * @author jntakpe
 */
module.exports = function (grunt) {
    'use strict';
    grunt.util.linefeed = '\n';

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bowerrc: grunt.file.readJSON('.bowerrc'),
        dirs: {
            bowerRes: '<%= bowerrc.directory %>',
            target: 'src/main/webapp/static'
        },
        bower: {
            install: {
                options: {
                    targetDir: '<%= dirs.bowerRes %>',
                    cleanTargetDir: true,
                    layout: 'byComponent',
                    install: true,
                    copy: false,
                    verbose: true
                }
            }
        },
        concat: {
            jsCore: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Third party JS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/jquery/dist/jquery.min.js',
                    '<%= dirs.bowerRes %>/bootstrap/dist/js/bootstrap.min.js',
                    '<%= dirs.bowerRes %>/iCheck/icheck.min.js',
                    '<%= dirs.bowerRes %>/angular/angular.min.js',
                    '<%= dirs.bowerRes %>/angular-route/angular-route.min.js',
                    '<%= dirs.bowerRes %>/angular-messages/angular-messages.min.js',
                    '<%= dirs.bowerRes %>/angular-resource/angular-resource.min.js',
                    '<%= dirs.bowerRes %>/angular-bootstrap/ui-bootstrap-tpls.min.js'
                ],
                dest: '<%= dirs.target %>/js/js-core.min.js'
            },
            cssCore: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Third party CSS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/bootstrap/dist/css/bootstrap.min.css',
                    '<%= dirs.bowerRes %>/font-awesome/css/font-awesome.min.css'
                ],
                dest: '<%= dirs.target %>/css/css-core.min.css'
            },
            cssTheme: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Theme CSS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/iCheck/skins/minimal/grey.css',
                    '<%= dirs.target %>/css/theme/styles.css',
                    '<%= dirs.target %>/css/theme/styles-responsive.css',
                    '<%= dirs.target %>/css/theme/plugins.css',
                    '<%= dirs.target %>/css/theme/incoming.css'
                ],
                dest: '<%= dirs.target %>/css/css-theme.css'
            }
        },

        copy: {
            fonts: {
                files: [
                    {
                        expand: true,
                        flatten: true,
                        src: [
                            '<%= dirs.bowerRes %>/bootstrap/dist/fonts/*',
                            '<%= dirs.bowerRes %>/font-awesome/fonts/*'
                        ],
                        dest: '<%= dirs.target %>/fonts',
                        filter: 'isFile'
                    }
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['concat:jsCore', 'concat:cssCore', 'concat:cssTheme', 'copy:fonts', 'copy:img']);
};