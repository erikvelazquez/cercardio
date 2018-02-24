(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medic', {
            parent: 'entity',
            url: '/medic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic/medics.html',
                    controller: 'MedicController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medic-detail', {
            parent: 'medic',
            url: '/medic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic/medic-detail.html',
                    controller: 'MedicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medic', function($stateParams, Medic) {
                    return Medic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medic-detail.edit', {
            parent: 'medic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic/medic-dialog.html',
                    controller: 'MedicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medic', function(Medic) {
                            return Medic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic.new', {
            parent: 'medic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic/medic-dialog.html',
                    controller: 'MedicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                colony: null,
                                street: null,
                                streetnumber: null,
                                suitnumber: null,
                                phonenumber1: null,
                                phonenumber2: null,
                                email1: null,
                                email2: null,
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                snapchat: null,
                                linkedin: null,
                                vine: null,
                                createdat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medic', null, { reload: 'medic' });
                }, function() {
                    $state.go('medic');
                });
            }]
        })
        .state('medic.edit', {
            parent: 'medic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic/medic-dialog.html',
                    controller: 'MedicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medic', function(Medic) {
                            return Medic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic', null, { reload: 'medic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic.delete', {
            parent: 'medic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic/medic-delete-dialog.html',
                    controller: 'MedicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medic', function(Medic) {
                            return Medic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic', null, { reload: 'medic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
