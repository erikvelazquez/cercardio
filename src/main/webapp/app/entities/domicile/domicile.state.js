(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('domicile', {
            parent: 'entity',
            url: '/domicile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.domicile.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/domicile/domiciles.html',
                    controller: 'DomicileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('domicile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('domicile-detail', {
            parent: 'domicile',
            url: '/domicile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.domicile.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/domicile/domicile-detail.html',
                    controller: 'DomicileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('domicile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Domicile', function($stateParams, Domicile) {
                    return Domicile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'domicile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('domicile-detail.edit', {
            parent: 'domicile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domicile/domicile-dialog.html',
                    controller: 'DomicileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Domicile', function(Domicile) {
                            return Domicile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domicile.new', {
            parent: 'domicile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domicile/domicile-dialog.html',
                    controller: 'DomicileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                domicile: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('domicile', null, { reload: 'domicile' });
                }, function() {
                    $state.go('domicile');
                });
            }]
        })
        .state('domicile.edit', {
            parent: 'domicile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domicile/domicile-dialog.html',
                    controller: 'DomicileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Domicile', function(Domicile) {
                            return Domicile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domicile', null, { reload: 'domicile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domicile.delete', {
            parent: 'domicile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domicile/domicile-delete-dialog.html',
                    controller: 'DomicileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Domicile', function(Domicile) {
                            return Domicile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domicile', null, { reload: 'domicile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
