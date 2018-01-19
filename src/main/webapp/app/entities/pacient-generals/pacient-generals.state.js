(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-generals', {
            parent: 'entity',
            url: '/pacient-generals',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientGenerals.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-generals/pacient-generals.html',
                    controller: 'PacientGeneralsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientGenerals');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-generals-detail', {
            parent: 'pacient-generals',
            url: '/pacient-generals/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientGenerals.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-generals/pacient-generals-detail.html',
                    controller: 'PacientGeneralsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientGenerals');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientGenerals', function($stateParams, PacientGenerals) {
                    return PacientGenerals.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-generals',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-generals-detail.edit', {
            parent: 'pacient-generals-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-generals/pacient-generals-dialog.html',
                    controller: 'PacientGeneralsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientGenerals', function(PacientGenerals) {
                            return PacientGenerals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-generals.new', {
            parent: 'pacient-generals',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-generals/pacient-generals-dialog.html',
                    controller: 'PacientGeneralsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                curp: null,
                                rfc: null,
                                dateofbirth: null,
                                placeofbirth: null,
                                privatenumber: null,
                                socialnumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-generals', null, { reload: 'pacient-generals' });
                }, function() {
                    $state.go('pacient-generals');
                });
            }]
        })
        .state('pacient-generals.edit', {
            parent: 'pacient-generals',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-generals/pacient-generals-dialog.html',
                    controller: 'PacientGeneralsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientGenerals', function(PacientGenerals) {
                            return PacientGenerals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-generals', null, { reload: 'pacient-generals' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-generals.delete', {
            parent: 'pacient-generals',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-generals/pacient-generals-delete-dialog.html',
                    controller: 'PacientGeneralsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientGenerals', function(PacientGenerals) {
                            return PacientGenerals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-generals', null, { reload: 'pacient-generals' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
