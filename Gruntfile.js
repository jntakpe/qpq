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
                dest: '<%= dirs.target %>/js/js-core.min.js'
            },
            cssCore: {
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
                dest: '<%= dirs.target %>/css/css-core.min.css'
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['concat:jsCore', 'concat:cssCore']);
};