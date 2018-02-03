(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-app', {
            parent: 'entity',
            url: '/pacient-app',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientApp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-app/pacient-apps.html',
                    controller: 'PacientAppController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientApp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-app-detail', {
            parent: 'pacient-app',
            url: '/pacient-app/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientApp.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-app/pacient-app-detail.html',
                    controller: 'PacientAppDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientApp');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientApp', function($stateParams, PacientApp) {
                    return PacientApp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-app',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-app-detail.edit', {
            parent: 'pacient-app-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-app/pacient-app-dialog.html',
                    controller: 'PacientAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientApp', function(PacientApp) {
                            return PacientApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-app.new', {
            parent: 'pacient-app',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-app/pacient-app-dialog.html',
                    controller: 'PacientAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                chronicdegenerative: null,
                                traumatic: null,
                                gynecologicalobstetrics: null,
                                others: null,
                                daytime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-app', null, { reload: 'pacient-app' });
                }, function() {
                    $state.go('pacient-app');
                });
            }]
        })
        .state('pacient-app.edit', {
            parent: 'pacient-app',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-app/pacient-app-dialog.html',
                    controller: 'PacientAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientApp', function(PacientApp) {
                            return PacientApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-app', null, { reload: 'pacient-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-app.delete', {
            parent: 'pacient-app',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-app/pacient-app-delete-dialog.html',
                    controller: 'PacientAppDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientApp', function(PacientApp) {
                            return PacientApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-app', null, { reload: 'pacient-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
