(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-laboratoy', {
            parent: 'entity',
            url: '/pacient-laboratoy',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientLaboratoy.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoys.html',
                    controller: 'PacientLaboratoyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientLaboratoy');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-laboratoy-detail', {
            parent: 'pacient-laboratoy',
            url: '/pacient-laboratoy/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientLaboratoy.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoy-detail.html',
                    controller: 'PacientLaboratoyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientLaboratoy');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientLaboratoy', function($stateParams, PacientLaboratoy) {
                    return PacientLaboratoy.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-laboratoy',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-laboratoy-detail.edit', {
            parent: 'pacient-laboratoy-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoy-dialog.html',
                    controller: 'PacientLaboratoyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientLaboratoy', function(PacientLaboratoy) {
                            return PacientLaboratoy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-laboratoy.new', {
            parent: 'pacient-laboratoy',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoy-dialog.html',
                    controller: 'PacientLaboratoyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                laboratory: null,
                                number: null,
                                dateofelaboration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-laboratoy', null, { reload: 'pacient-laboratoy' });
                }, function() {
                    $state.go('pacient-laboratoy');
                });
            }]
        })
        .state('pacient-laboratoy.edit', {
            parent: 'pacient-laboratoy',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoy-dialog.html',
                    controller: 'PacientLaboratoyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientLaboratoy', function(PacientLaboratoy) {
                            return PacientLaboratoy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-laboratoy', null, { reload: 'pacient-laboratoy' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-laboratoy.delete', {
            parent: 'pacient-laboratoy',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-laboratoy/pacient-laboratoy-delete-dialog.html',
                    controller: 'PacientLaboratoyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientLaboratoy', function(PacientLaboratoy) {
                            return PacientLaboratoy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-laboratoy', null, { reload: 'pacient-laboratoy' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
