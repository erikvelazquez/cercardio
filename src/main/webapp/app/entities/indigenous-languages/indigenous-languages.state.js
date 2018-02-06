(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('indigenous-languages', {
            parent: 'entity',
            url: '/indigenous-languages',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.indigenous_Languages.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages.html',
                    controller: 'Indigenous_LanguagesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('indigenous_Languages');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('indigenous-languages-detail', {
            parent: 'indigenous-languages',
            url: '/indigenous-languages/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.indigenous_Languages.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages-detail.html',
                    controller: 'Indigenous_LanguagesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('indigenous_Languages');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Indigenous_Languages', function($stateParams, Indigenous_Languages) {
                    return Indigenous_Languages.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'indigenous-languages',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('indigenous-languages-detail.edit', {
            parent: 'indigenous-languages-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages-dialog.html',
                    controller: 'Indigenous_LanguagesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Indigenous_Languages', function(Indigenous_Languages) {
                            return Indigenous_Languages.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('indigenous-languages.new', {
            parent: 'indigenous-languages',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages-dialog.html',
                    controller: 'Indigenous_LanguagesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                catalogkey: null,
                                indigenouslanguage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('indigenous-languages', null, { reload: 'indigenous-languages' });
                }, function() {
                    $state.go('indigenous-languages');
                });
            }]
        })
        .state('indigenous-languages.edit', {
            parent: 'indigenous-languages',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages-dialog.html',
                    controller: 'Indigenous_LanguagesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Indigenous_Languages', function(Indigenous_Languages) {
                            return Indigenous_Languages.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('indigenous-languages', null, { reload: 'indigenous-languages' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('indigenous-languages.delete', {
            parent: 'indigenous-languages',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indigenous-languages/indigenous-languages-delete-dialog.html',
                    controller: 'Indigenous_LanguagesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Indigenous_Languages', function(Indigenous_Languages) {
                            return Indigenous_Languages.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('indigenous-languages', null, { reload: 'indigenous-languages' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
