(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('living-place', {
            parent: 'entity',
            url: '/living-place',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.livingPlace.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/living-place/living-places.html',
                    controller: 'LivingPlaceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('livingPlace');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('living-place-detail', {
            parent: 'living-place',
            url: '/living-place/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.livingPlace.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/living-place/living-place-detail.html',
                    controller: 'LivingPlaceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('livingPlace');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LivingPlace', function($stateParams, LivingPlace) {
                    return LivingPlace.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'living-place',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('living-place-detail.edit', {
            parent: 'living-place-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/living-place/living-place-dialog.html',
                    controller: 'LivingPlaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LivingPlace', function(LivingPlace) {
                            return LivingPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('living-place.new', {
            parent: 'living-place',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/living-place/living-place-dialog.html',
                    controller: 'LivingPlaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('living-place', null, { reload: 'living-place' });
                }, function() {
                    $state.go('living-place');
                });
            }]
        })
        .state('living-place.edit', {
            parent: 'living-place',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/living-place/living-place-dialog.html',
                    controller: 'LivingPlaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LivingPlace', function(LivingPlace) {
                            return LivingPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('living-place', null, { reload: 'living-place' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('living-place.delete', {
            parent: 'living-place',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/living-place/living-place-delete-dialog.html',
                    controller: 'LivingPlaceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LivingPlace', function(LivingPlace) {
                            return LivingPlace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('living-place', null, { reload: 'living-place' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
