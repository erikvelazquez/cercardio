(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('programs', {
            parent: 'entity',
            url: '/programs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.programs.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programs/programs.html',
                    controller: 'ProgramsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programs');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('programs-detail', {
            parent: 'programs',
            url: '/programs/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.programs.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/programs/programs-detail.html',
                    controller: 'ProgramsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programs');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Programs', function($stateParams, Programs) {
                    return Programs.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'programs',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('programs-detail.edit', {
            parent: 'programs-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programs/programs-dialog.html',
                    controller: 'ProgramsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Programs', function(Programs) {
                            return Programs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programs.new', {
            parent: 'programs',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programs/programs-dialog.html',
                    controller: 'ProgramsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                url: null,
                                icon: null,
                                programparentid: null,
                                byorder: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('programs', null, { reload: 'programs' });
                }, function() {
                    $state.go('programs');
                });
            }]
        })
        .state('programs.edit', {
            parent: 'programs',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programs/programs-dialog.html',
                    controller: 'ProgramsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Programs', function(Programs) {
                            return Programs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programs', null, { reload: 'programs' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('programs.delete', {
            parent: 'programs',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/programs/programs-delete-dialog.html',
                    controller: 'ProgramsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Programs', function(Programs) {
                            return Programs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('programs', null, { reload: 'programs' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
