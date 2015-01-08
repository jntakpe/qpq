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
            source: 'src/main/webapp/static',
            target: 'src/main/webapp/dist'
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
            jsThirdParty: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Core JS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/jquery/dist/jquery.min.js',
                    '<%= dirs.bowerRes %>/hammerjs/hammer.min.js',
                    '<%= dirs.bowerRes %>/angular/angular.min.js',
                    '<%= dirs.bowerRes %>/angular-route/angular-route.min.js',
                    '<%= dirs.bowerRes %>/angular-messages/angular-messages.min.js',
                    '<%= dirs.bowerRes %>/angular-resource/angular-resource.min.js',
                    '<%= dirs.bowerRes %>/angular-animate/angular-animate.min.js',
                    '<%= dirs.bowerRes %>/angular-aria/angular-aria.js',
                    '<%= dirs.bowerRes %>/angular-material/angular-material.min.js'
                ],
                dest: '<%= dirs.target %>/js/third-party.min.js'
            },
            cssThirdParty: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Core CSS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/angular-material/angular-material.min.css'
                ],
                dest: '<%= dirs.target %>/css/third-party.min.css'
            },
            coreJs: {
                src: [
                    '<%= dirs.source %>/js/qpq.js',
                    '<%= dirs.source %>/js/home/home.controller.js'
                ],
                dest: '<%= dirs.target %>/js/core.js'
            }
        },
        watch: {
            js: {
                files: '<%= dirs.source %>/js/**/*.js',
                tasks: ['concat:coreJs']
            },
            css: {
                files: '<%= dirs.source %>/css/**/*.css',
                tasks: ['concat:coreCss']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['concat:jsThirdParty', 'concat:cssThirdParty', 'concat:coreJs']);
};