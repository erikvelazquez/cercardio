(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-apnp', {
            parent: 'entity',
            url: '/pacient-apnp',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientApnp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnps.html',
                    controller: 'PacientApnpController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientApnp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-apnp-detail', {
            parent: 'pacient-apnp',
            url: '/pacient-apnp/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientApnp.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnp-detail.html',
                    controller: 'PacientApnpDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientApnp');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientApnp', function($stateParams, PacientApnp) {
                    return PacientApnp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-apnp',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-apnp-detail.edit', {
            parent: 'pacient-apnp-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnp-dialog.html',
                    controller: 'PacientApnpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientApnp', function(PacientApnp) {
                            return PacientApnp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-apnp.new', {
            parent: 'pacient-apnp',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnp-dialog.html',
                    controller: 'PacientApnpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                datestarts: null,
                                dateend: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-apnp', null, { reload: 'pacient-apnp' });
                }, function() {
                    $state.go('pacient-apnp');
                });
            }]
        })
        .state('pacient-apnp.edit', {
            parent: 'pacient-apnp',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnp-dialog.html',
                    controller: 'PacientApnpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientApnp', function(PacientApnp) {
                            return PacientApnp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-apnp', null, { reload: 'pacient-apnp' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-apnp.delete', {
            parent: 'pacient-apnp',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-apnp/pacient-apnp-delete-dialog.html',
                    controller: 'PacientApnpDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientApnp', function(PacientApnp) {
                            return PacientApnp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-apnp', null, { reload: 'pacient-apnp' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
