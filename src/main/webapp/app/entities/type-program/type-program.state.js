(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-program', {
            parent: 'entity',
            url: '/type-program',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.type_Program.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-program/type-programs.html',
                    controller: 'Type_ProgramController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('type_Program');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-program-detail', {
            parent: 'type-program',
            url: '/type-program/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.type_Program.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-program/type-program-detail.html',
                    controller: 'Type_ProgramDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('type_Program');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Type_Program', function($stateParams, Type_Program) {
                    return Type_Program.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-program',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-program-detail.edit', {
            parent: 'type-program-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-program/type-program-dialog.html',
                    controller: 'Type_ProgramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Type_Program', function(Type_Program) {
                            return Type_Program.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-program.new', {
            parent: 'type-program',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-program/type-program-dialog.html',
                    controller: 'Type_ProgramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-program', null, { reload: 'type-program' });
                }, function() {
                    $state.go('type-program');
                });
            }]
        })
        .state('type-program.edit', {
            parent: 'type-program',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-program/type-program-dialog.html',
                    controller: 'Type_ProgramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Type_Program', function(Type_Program) {
                            return Type_Program.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-program', null, { reload: 'type-program' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-program.delete', {
            parent: 'type-program',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-program/type-program-delete-dialog.html',
                    controller: 'Type_ProgramDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Type_Program', function(Type_Program) {
                            return Type_Program.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-program', null, { reload: 'type-program' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
