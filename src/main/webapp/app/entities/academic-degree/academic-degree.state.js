(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('academic-degree', {
            parent: 'entity',
            url: '/academic-degree',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.academicDegree.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/academic-degree/academic-degrees.html',
                    controller: 'AcademicDegreeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('academicDegree');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('academic-degree-detail', {
            parent: 'academic-degree',
            url: '/academic-degree/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.academicDegree.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/academic-degree/academic-degree-detail.html',
                    controller: 'AcademicDegreeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('academicDegree');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AcademicDegree', function($stateParams, AcademicDegree) {
                    return AcademicDegree.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'academic-degree',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('academic-degree-detail.edit', {
            parent: 'academic-degree-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-degree/academic-degree-dialog.html',
                    controller: 'AcademicDegreeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AcademicDegree', function(AcademicDegree) {
                            return AcademicDegree.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('academic-degree.new', {
            parent: 'academic-degree',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-degree/academic-degree-dialog.html',
                    controller: 'AcademicDegreeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                isactive: null,
                                createdat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('academic-degree', null, { reload: 'academic-degree' });
                }, function() {
                    $state.go('academic-degree');
                });
            }]
        })
        .state('academic-degree.edit', {
            parent: 'academic-degree',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-degree/academic-degree-dialog.html',
                    controller: 'AcademicDegreeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AcademicDegree', function(AcademicDegree) {
                            return AcademicDegree.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('academic-degree', null, { reload: 'academic-degree' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('academic-degree.delete', {
            parent: 'academic-degree',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-degree/academic-degree-delete-dialog.html',
                    controller: 'AcademicDegreeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AcademicDegree', function(AcademicDegree) {
                            return AcademicDegree.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('academic-degree', null, { reload: 'academic-degree' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
